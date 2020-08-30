package com.architect.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "account")
@Entity
@Setter
@Getter
public class AccountEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "accountIdSeq", sequenceName = "account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "accountIdSeq")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "balance")
    private BigDecimal balance;
}
