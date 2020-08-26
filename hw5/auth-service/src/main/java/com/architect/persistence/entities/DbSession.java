package com.architect.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name =  "\"session\"", schema = "public")
public class DbSession extends BaseEntity {
    @Id
    @SequenceGenerator(name = "sessionIdSeq", sequenceName = "session_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sessionIdSeq")
    private Long id;

    @Column(name = "token")
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private DbUser user;
}
