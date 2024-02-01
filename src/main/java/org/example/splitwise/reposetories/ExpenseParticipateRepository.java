package org.example.splitwise.reposetories;

import org.example.splitwise.entities.Expense_participate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseParticipateRepository extends JpaRepository<Expense_participate, Long> {


    @Query(
            value = "SELECT *\n" +
                    "from expense_participates as ep\n" +
                    "where ep.expense_id = ?1",
            nativeQuery = true
    )
    List<Expense_participate> findAllByExpenseId(Long Expense_id);



}
