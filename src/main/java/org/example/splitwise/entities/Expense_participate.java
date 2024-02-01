package org.example.splitwise.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expense_participates")
public class Expense_participate {

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

    @Column(name = "share")
    private double share;

}
