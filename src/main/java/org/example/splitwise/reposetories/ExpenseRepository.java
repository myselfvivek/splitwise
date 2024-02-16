package org.example.splitwise.reposetories;

import org.example.splitwise.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ExpenseRepository extends JpaRepository<Expense, Long> {


    @Query(
            value = "SELECT ex.* \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where (expt.participate_id = ?1 OR ex.user_id = ?1) AND ex.group_id = null",
            nativeQuery = true
    )
    public List<Expense> getAllExpenseByUserId(Long user_id);


    @Query(
            value = "SELECT ex.* \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where (expt.participate_id = ?2 OR ex.user_id = ?2) AND ex.group_id = ?1",
            nativeQuery = true
    )
    public List<Expense> getAllExpenseByGroupId(Long group_id, Long user_id);

    @Query(
            value = "SELECT ex.* \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where (expt.participate_id = ?1 OR ex.user_id = ?1) AND ex.expense_id = ?2",
            nativeQuery = true
    )
    public Expense getExpenseByUserIdExpenseId(Long user_id, Long expense_id);

    @Query(
            value = "SELECT COALESCE(sum(share), 0.0) \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where ex.user_id = ?1 AND expt.participate_id = ?2",
            nativeQuery = true
    )
    public double getSettlementByUserIds(Long Payer, Long Ower);


    @Query(
            value = "SELECT COALESCE(sum(share), 0.0) \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where ex.user_id = ?1 AND expt.participate_id = ?2 AND ex.group_id = ?3",
            nativeQuery = true
    )
    public double getSettlementByUserIdsGroupId(Long Payer, Long Ower, Long groupId);



    @Query(
            value = "SELECT COALESCE(sum(amount), 0.0) \n" +
                    "from expenses as ex\n" +
                    "where ex.user_id = ?1 AND ex.group_id = ?2",
            nativeQuery = true
    )
    public double getSettlementAsPayerInGroup(Long Payer, Long groupId);

    @Query(
            value = "SELECT COALESCE(sum(share), 0.0) \n" +
                    "from expenses as ex JOIN expense_participates as expt ON ex.expense_id = expt.expense_id\n" +
                    "where expt.participate_id = ?1 AND ex.group_id = ?2",
            nativeQuery = true
    )
    public double getSettlementAsOwerInGroup(Long ower, Long groupId);

    @Query(
            value = "SELECT COALESCE(sum(amount), 0.0) \n" +
                    "from expenses as ex\n" +
                    "where ex.user_id = ?1",
            nativeQuery = true
    )
    public double getSettlementAsPayer(Long userId);


    @Query(
            value = "SELECT COALESCE(sum(share), 0.0) \n" +
                    "from expense_participates as ex\n" +
                    "where ex.participate_id = ?1",
            nativeQuery = true
    )
    public double getSettlementAsOwer(Long userId);

}
