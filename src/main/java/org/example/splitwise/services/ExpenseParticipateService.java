package org.example.splitwise.services;

import org.example.splitwise.entities.Expense_participate;
import org.example.splitwise.reposetories.ExpenseParticipateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseParticipateService {

    @Autowired
    private ExpenseParticipateRepository expenseParticipateRepository;


    public void addExpense_participation(Expense_participate expenseParticipate)
    {
        expenseParticipateRepository.save(expenseParticipate);
    }
    public List<Expense_participate> findAllExpense_participateByExpenseId(Long expense_id)
    {
        return expenseParticipateRepository.findAllByExpenseId(expense_id).stream().toList();
    }




}
