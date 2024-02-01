package org.example.splitwise.reposetories;

import org.example.splitwise.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query(
            value = "SELECT sum(share) \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where ex.user_id = ?1 AND expt.participate_id = ?2",
            nativeQuery = true
    )
    int getSettlementByUserIds(Long Payer, Long Ower);


}
