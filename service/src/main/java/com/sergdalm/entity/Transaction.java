package com.sergdalm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@ToString(exclude = {"fromAccount", "toAccount"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Account fromAccount;

    @ManyToOne(optional = false)
    private Account toAccount;

    @Column(nullable = false)
    private Integer transferAmount;

    @Column(nullable = false)
    private LocalDateTime transferredAt;

    public void setFromAccount(Account account) {
        this.fromAccount = account;
        account.getTransactions().add(this);
    }

    public void setToAccount(Account account) {
        this.toAccount = account;
        account.getTransactions().add(this);
    }
}
