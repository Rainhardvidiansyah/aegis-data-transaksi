package com.aegis.transaksi.service;

import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.entity.Products;
import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.enums.TransactionStatus;
import com.aegis.transaksi.exceptions.ProductNotFoundException;
import com.aegis.transaksi.repository.ProductRepository;
import com.aegis.transaksi.repository.TransactionItemRepository;
import com.aegis.transaksi.repository.TransactionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    // menciptakan layer utama untuk laporan.
    // Jadi transaksi ini adalah kumpulan dari data yang ada detail transaksi
    // dalam detail transaksi ada product dan kuantitas product yang dibeli


    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final TransactionItemRepository transactionItemRepository;

    public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository,
                              TransactionItemRepository transactionItemRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.transactionItemRepository = transactionItemRepository;
    }


    @Transactional
    public Transaction createTransaction(List<TransactionRequestDto> transactionRequest) {

        logger.info("This method is called");

        Transaction transaction = new Transaction();

        List<TransactionItem> items = new ArrayList<>();

        transaction.setStatus(TransactionStatus.COMPLETED);

        transaction.setTransactionDate(LocalDateTime.now());

        transaction = this.transactionRepository.save(transaction);


        for (TransactionRequestDto requestDto : transactionRequest) {

            UUID productId = UUID.fromString(requestDto.getProductId());

            var product = this.productRepository.findById(productId);

            Products products = product.get();

            if (!product.isPresent()) {
                throw new ProductNotFoundException("Product not found");
            }

            TransactionItem transactionItem = new TransactionItem();

            transactionItem.setProduct(products);

            transactionItem.setPrice(products.getPrice());

            transactionItem.setQuantity(requestDto.getQuantity());

            BigDecimal quantities = BigDecimal.valueOf(requestDto.getQuantity());
            BigDecimal totalPrice = products.getPrice().multiply(quantities);


            if (totalPrice == null) {
                throw new RuntimeException("Failed to calculate total price");
            }

            logger.info("items: {}", items);
            logger.info("product: {}", products);

            logger.info("TOTAL PRICE: {}", transactionItem.getTotalPrice());

            transactionItem.setTotalPrice(totalPrice);


            transactionItem.setTransaction(transaction);

            int newStocks = products.getStocks() - requestDto.getQuantity();
            products.setStocks(newStocks);

            items.add(transactionItem);
            List<TransactionItem> listOfTransactionItem = this.transactionItemRepository.saveAll(items);

            listOfTransactionItem.forEach(System.out::println);

            Transaction finalTransaction = transaction;
            listOfTransactionItem.forEach(transactionItem1 -> transactionItem1.setTransaction(finalTransaction));


            logger.info("listOfTransactionItem IS: {}", listOfTransactionItem);


            var transactionId = transaction.getTransactionId();

            BigDecimal totalAmount = listOfTransactionItem.stream()
                    .filter(item -> item.getTransaction().getTransactionId().equals(transactionId))
                    .map(TransactionItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            logger.info("TOTAL AMOUNT: {}", totalAmount);

            if (totalAmount != null) {
                transaction.setTotalAmount(totalAmount);
            } else {
                throw new RuntimeException("FAILED TO CALCULATE TOTAL AMOUNT");
            }
            logger.info("Transaction Item: {}", transactionItem);
        }

        logger.info("transaction amount: {}", transaction.getTotalAmount());
        transaction.addTransactionItem(items);
        return this.transactionRepository.save(transaction);
    }


    @Transactional
    public void addTransaction(UUID productId, int quantity) {

        var transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());

        List<TransactionItem> items = new ArrayList<>();

        this.transactionRepository.save(transaction);

        var productOptional = this.productRepository.findById(productId);

        var transactionItem = new TransactionItem();

        var product = productOptional.get();

        transactionItem.setProduct(product);

        transactionItem.setPrice(product.getPrice());
        transactionItem.setQuantity(quantity);

        BigDecimal quantities = BigDecimal.valueOf(quantity);
        BigDecimal totalPrice = product.getPrice().multiply(quantities);

        transactionItem.setTotalPrice(totalPrice);

        transactionItem.setTransaction(transaction);

        items.add(transactionItem);
        transaction.addTransactionItem(items);
        transaction.setTotalAmount(transactionItem.getTotalPrice().multiply(quantities));

        this.transactionRepository.save(transaction);

    }


    //find transaction
    //if found, then get transaction_item
    //if transaction_item is found, then
    //get product from transaction_item and
    //add with newQuantity
    @Transactional
    public Transaction addProductAndUpdateTotalPrice(String transactionId, String transactionItemsid, int quantity) {
        logger.info("addProductAndUpdateTotalPrice");


        UUID transactionUuid = UUID.fromString(transactionId);
        var transactionOptional = transactionRepository.findById(transactionUuid);

        Transaction transaction = transactionOptional
                .orElseThrow(() -> new RuntimeException("Transaction not found TTTTTTTTTT"));


        if (transaction == null) {
            throw new RuntimeException("Transaction not found");
        }

        UUID transactionItemUuid = UUID.fromString(transactionItemsid);

        var transactionItem = transaction.getTransactionItem().stream()
                .filter(item -> item.getTransactionItemId().equals(transactionItemUuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Transaction Item not found"));

        //validate that specific transaction_id belongs to some or many
        //transaction item.
        //Without validating it, the program will be chaos
        if (!transaction.getTransactionId().equals(transactionItem.getTransaction().getTransactionId())) {
            throw new IllegalStateException("Id is not same");
        }


        //start to update quantity of product in transaction_item

        int newQuantityInTransactionItems = transactionItem.getQuantity() + quantity;

        //get product price
        //product price here should be multiplied by newQuantityInTransactionItems

        BigDecimal productPrice = transactionItem.getProduct().getPrice();
        BigDecimal newTotalPrice = productPrice.multiply(BigDecimal.valueOf(newQuantityInTransactionItems));

        transactionItem.setQuantity(newQuantityInTransactionItems);
        reduceStockInProduct(transactionItem.getProduct().getProductId(), quantity);

        transactionItem.setTotalPrice(newTotalPrice);


        //insert Transaction Item to list of transaction item
        List<TransactionItem> items = new ArrayList<>();
        items.add(transactionItem);


        BigDecimal totalAmount = items.stream()
                .filter(item -> item.getTransaction().getTransactionId().equals(transaction.getTransactionId()))
                .map(TransactionItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        transaction.setTotalAmount(totalAmount);

        transactionItemRepository.saveAll(items);

        transaction.getTransactionItem().clear();
        transaction.getTransactionItem().addAll(items);


        //transaction.addTransactionItem(items);
        transaction.addAllItem(items);


        return transactionRepository.save(transaction);
    }

    // error: A collection with cascade="all-delete-orphan" was no longer referenced by the owning entity instance: com.aegis.transaksi.entity.Transaction.transactionItem
    /*
    Begini langkahnya:
    cari transaksinya dulu.
    ada transaksinya? oh, ada.
    ambil transaction itemnya.
    ada transaction item? yup, ada.

    Jika ada jangan lupa untuk disamakan bahwa: satu transaction id
    bisa terkait dengan banyak transaction_item_id. Harus divalidasi dulu!

    Pada transaction_item, kita punya beberapa variable yang bisa digunakan:
    "harga satuan" dikali kuantitas baru (di param).
    "Kuantitas" yang ada ditambah kuantitas baru (di param).
     */


    @Transactional
    private void reduceStockInProduct(UUID productId, int quantity) {

        var productOptional = this.productRepository.findById(productId);

        if (!productOptional.isPresent()) {
            throw new ProductNotFoundException("Product not found");
        }

        Products products = productOptional.get();

        int newStocks = products.getStocks() - quantity;

        if (newStocks < 0) {
            throw new IllegalStateException("Out of Stock");
        }

        products.setStocks(newStocks);

        productRepository.save(products);
    }


    @Transactional
    public Transaction subtractTransactionAndUpdateTotalAMount(String transactionId, String transactionItemId, int quantity) {

        UUID transactionUuid = UUID.fromString(transactionId);
        UUID transactionItemUuid = UUID.fromString(transactionItemId);

        List<TransactionItem> items = new ArrayList<>();

        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionUuid);
        var transaction = transactionOptional.orElseThrow(() -> new RuntimeException("Could not find transaction"));

        var transactionItem = transaction.getTransactionItem().stream()
                .filter(item -> item.getTransactionItemId().equals(transactionItemUuid))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find transaction item"));

        //validate that specific transaction_id belongs to some or many
        //transaction item.
        //Without validating it, the program will be chaos
        if (!transaction.getTransactionId().equals(transactionItem.getTransaction().getTransactionId())) {
            throw new IllegalStateException("Id is not same!!");
        }

        BigDecimal productPrice = transactionItem.getProduct().getPrice();

        int newQuantity = transactionItem.getQuantity() - quantity;

        BigDecimal totalPrice = productPrice.multiply(BigDecimal.valueOf(newQuantity));

        transactionItem.setTotalPrice(totalPrice);


        items.add(transactionItem);
        //recount the total price by counting for each total price that belongs to transaction_item
        //then this total amount variable will be set at transaction total amount
        BigDecimal totalAmount = items.stream()
                .filter(item -> item.getTransaction().getTransactionId().equals(transaction.getTransactionId()))
                .map(TransactionItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::subtract);


        this.subtractProductFromTTransaction(transactionItem.getProduct().getProductId(), newQuantity);

        transactionItemRepository.saveAll(items);

        transaction.getTransactionItem().clear();
        transaction.getTransactionItem().addAll(items);
        transaction.setTransactionItem(items);
        transaction.setTotalAmount(totalAmount);

        return transactionRepository.save(transaction);
    }


    @Transactional
    private void subtractProductFromTTransaction(UUID productId, int quantity) {

        var product = productRepository.findById(productId);

        Products products = product.orElseThrow(() -> new RuntimeException("Product not found"));

        int newStock = products.getStocks() + quantity;

        products.setStocks(newStock);

        productRepository.save(products);
    }


    //GET TRANSACTION BY ID
    public Transaction getTransactionById(String transactionId) {

        UUID transactionUuid = UUID.fromString(transactionId);

        var transactionOptional = transactionRepository.findById(transactionUuid);

        return transactionOptional.orElseThrow(() -> new RuntimeException("Could not find transaction"));
    }


    //GET ALL TRANSACTIONS
    public Iterable<Transaction> getAllTransactions() {
        return this.transactionRepository.findAll();
    }


}


/*
sebagai kasir saya akan:
1. temukan barang -> find product by id
2. jika ada, saya cari harganya
3. setelah tahu harganya lalu
4. saya masukkan kuantitas
5. dengan adanya harga dan kuantitas, saya akan
6. jumlahkan kuantitas dan harga dengan mengalikannya.
7.
 */










