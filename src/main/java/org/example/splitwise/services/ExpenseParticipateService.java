package org.example.splitwise.services;

import org.example.splitwise.entities.ExpenseParticipate;
import org.example.splitwise.reposetories.ExpenseParticipateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExpenseParticipateService {

    @Autowired
    private ExpenseParticipateRepository expenseParticipateRepository;


    public void addExpense_participation(ExpenseParticipate expenseParticipate)
    {
        expenseParticipateRepository.save(expenseParticipate);
    }
    public List<ExpenseParticipate> findAllExpense_participateByExpenseId(Long expense_id)
    {
        return expenseParticipateRepository.findAllByExpenseId(expense_id).stream().toList();
    }




}
