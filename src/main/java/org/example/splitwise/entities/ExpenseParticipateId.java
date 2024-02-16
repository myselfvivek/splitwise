package org.example.splitwise.entities;

import jakarta.persistence.*;

import java.io.Serializable;

public class ExpenseParticipateId implements Serializable {

    @Id
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "expense_id"
    )
    private Expense expense_id;

    @Id
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "participate_id"
    )
    private User participate_id;

}
