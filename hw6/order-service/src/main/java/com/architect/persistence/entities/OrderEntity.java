package com.architect.persistence.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "order")
@Entity
public class OrderEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "orderIdSeq", sequenceName = "order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orderIdSeq")
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "user_id")
    private Long userId;
}
