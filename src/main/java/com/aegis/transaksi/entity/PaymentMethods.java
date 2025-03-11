package com.aegis.transaksi.entity;


import com.aegis.transaksi.enums.EPaymentMethods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "payment_methods")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PaymentMethods {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_name")
    private EPaymentMethods paymentMethods;

    //make relation with transaction
//    @OneToMany(mappedBy = "paymentMethods", fetch = FetchType.LAZY)
//    private Transaction transaction;




}
