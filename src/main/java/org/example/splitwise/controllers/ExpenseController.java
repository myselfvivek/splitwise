package org.example.splitwise.controllers;

import org.example.splitwise.dto.expenseDTO;
import org.example.splitwise.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;


//    all the expenses

    @RequestMapping(value = "/api/expense/{id}", method = RequestMethod.GET)
    public expenseDTO getExpenseById(@PathVariable Long id)
    {
        return expenseService.getExpenseById(id);
    }
    @RequestMapping(value = "/api/expense/{user1}/{user2}", method = RequestMethod.GET)
    public void findExpense


}
