package org.example.splitwise.reposetories;

import org.example.splitwise.entities.ExpenseParticipate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ExpenseParticipateRepository extends JpaRepository<ExpenseParticipate, Long> {


    @Query(
            value = "SELECT *\n" +
                    "from expense_participates as ep\n" +
                    "where ep.expense_id = ?1",
            nativeQuery = true
    )
    List<ExpenseParticipate> findAllByExpenseId(Long Expense_id);



}
