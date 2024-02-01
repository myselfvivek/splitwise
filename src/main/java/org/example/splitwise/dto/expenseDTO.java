package org.example.splitwise.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Builder
public class expenseDTO {

    private Long expense_id;

    private double amount;

    private String description;

    private Long payer_id;

    private Long group_id;

    private Date date;

    private Map<Long, Double> participate_shares;
}
