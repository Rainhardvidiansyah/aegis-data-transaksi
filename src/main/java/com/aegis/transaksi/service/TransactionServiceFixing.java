package com.aegis.transaksi.service;

import com.aegis.transaksi.dto.TransactionRequestDto;
import com.aegis.transaksi.entity.Products;
import com.aegis.transaksi.entity.Transaction;
import com.aegis.transaksi.entity.TransactionItem;
import com.aegis.transaksi.enums.TransactionStatus;
import com.aegis.transaksi.repository.ProductRepository;
import com.aegis.transaksi.repository.TransactionItemRepository;
import com.aegis.transaksi.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TransactionServiceFixing {


    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceFixing.class);

    private final TransactionItemRepository  transactionItemRepository;

    private final TransactionRepository transactionRepository;

    private final ProductRepository productRepository;



    @Autowired
    public TransactionServiceFixing(TransactionItemRepository transactionItemRepository,
                                    TransactionRepository transactionRepository,
                                    ProductRepository productRepository) {
        this.transactionItemRepository = transactionItemRepository;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    /*
    UUID transactionItemId, Transaction transaction, Products product, Integer quantity
    BigDecimal price, BigDecimal totalPrice
     */
    


    @Transactional
    public Transaction createTransaction(List<TransactionRequestDto> transactionRequest) {

        logger.info("This method is called");

        Transaction transaction = new Transaction();


        List<TransactionItem> items = new ArrayList<>();

        transaction.setStatus(TransactionStatus.COMPLETED);

        transaction.setTransactionDate(LocalDateTime.now());
        transaction = this.transactionRepository.save(transaction);


        for(TransactionRequestDto requestDto : transactionRequest){

            var product = this.productRepository.findById(requestDto.getProductId());
            logger.info("product id: {}", requestDto.getProductId());
            Products products = product.get();

            if(!product.isPresent()){
                throw new RuntimeException("Product not found");
            }


                TransactionItem transactionItem = new TransactionItem();

                transactionItem.setProduct(products);

                transactionItem.setPrice(products.getPrice());

                transactionItem.setQuantity(requestDto.getQuantity());

                BigDecimal quantities = BigDecimal.valueOf(requestDto.getQuantity());


                BigDecimal totalPrice = products.getPrice().multiply(quantities);


                if(totalPrice == null) {
                    throw new RuntimeException("Failed to calculate total price");
                }

                logger.info("items: {}", items);
                logger.info("product: {}", products);

                logger.info("TOTAL PRICE: {}", transactionItem.getTotalPrice());

                transactionItem.setTotalPrice(totalPrice);


                transactionItem.setTransaction(transaction);  // Menghubungkan item dengan transaksi

                int newStocks = products.getStocks() - requestDto.getQuantity();
                products.setStocks(newStocks);

                items.add(transactionItem);
                List<TransactionItem> listOfTransactionItem = this.transactionItemRepository.saveAll(items);

                listOfTransactionItem.forEach(System.out::println);

                Transaction finalTransaction = transaction;
                listOfTransactionItem.stream().forEach(transactionItem1 -> transactionItem1.setTransaction(finalTransaction));


                logger.info("listOfTransactionItem IS: {}", listOfTransactionItem);


                var transactionId = transaction.getTransactionId();


                BigDecimal totalAmount = listOfTransactionItem.stream()
                        .filter(item -> item.getTransaction().getTransactionId().equals(transactionId))
                        .map(TransactionItem::getTotalPrice) // Menggunakan totalPrice alih-alih price
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                logger.info("TOTAL AMOUNT: {}", totalAmount);

                if(totalAmount != null){
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
    public void addTransaction(UUID productId, int quantity){

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


            /*
    UUID transactionItemId, Transaction transaction, Products product, Integer quantity
    BigDecimal price, BigDecimal totalPrice
     */



/*
        Alur refund:
    Temukan transaksi id
    Jika ada, lihat transaksi item id-nya
    Dengan transaksi_item_id kita bisa lempar id-nya parameter service.
    Ada? Ya ->
    Kembalikan jumlah product. Misalnya, tercatat di record: product 1 laku 10,
    ternyata refund. Di sinilah 10 stok product akan ditambahkan pada yang ada di database.

     */
