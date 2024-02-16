package org.example.splitwise.controllers;

import org.example.splitwise.dto.expenseDTO;
import org.example.splitwise.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/api/expense/{expense_id}/user/{user_id}", method = RequestMethod.GET)
    public expenseDTO getExpenseByUserIdExpenseId(@PathVariable Long user_id, @PathVariable Long expense_id)
    {
        System.out.println("hi");
        return expenseService.getExpenseByUserIdExpenseId(user_id, expense_id);
    }

    @RequestMapping(value = "/api/expense/user/{user_id}", method = RequestMethod.GET)
    public List<expenseDTO> getAllExpenseByUserId(@PathVariable Long user_id)
    {
        return expenseService.getAllExpenseByUserId(user_id);
    }

    @RequestMapping(value = "/api/expense/user/{user_id}/group/{group_id}", method = RequestMethod.GET)
    public List<expenseDTO> getAllExpenseByGroupId(@PathVariable Long group_id, @PathVariable Long user_id)
    {
        return expenseService.getAllExpenseByGroupId(group_id, user_id);
    }

//    workon this
//    @RequestMapping(value = "/api/expese/user/{user_id}", method = RequestMethod.GET)
//    public List<expenseDTO> getAllExpenseByuser

    @RequestMapping(value = "/api/settlement/user1/{user1_id}/user2/{user2_id}", method = RequestMethod.GET)
    public double getSettlementByUserIds(@PathVariable Long user1_id, @PathVariable Long user2_id){

        if(user1_id == null || user2_id == null) return 0;
        return expenseService.getSettlementByUserIds(user1_id, user2_id);
    }

    @RequestMapping(value = "/api/settlement/user1/{user1}/user2/{user2}/group/{groupId}", method = RequestMethod.GET)
    public double getSettlementByUserAndGroupIds(@PathVariable Long user1, @PathVariable Long user2, @PathVariable Long groupId)
    {
        if(user1 == null || user2 == null || groupId == null) return 0;
        return expenseService.getSettlementByUserAndGroupIds(user1, user2, groupId);
    }

    @RequestMapping(value = "/api/settlement/user/{user1}/group/{groupId}", method = RequestMethod.GET)
    public double getSettlementByGroupIds(@PathVariable Long user1, @PathVariable Long groupId)
    {
        if(user1 == null || groupId == null) return 0;
        return expenseService.getSettlementByGroupIds(user1, groupId);
    }

    @RequestMapping(value = "/api/settlement/user/{user1}", method = RequestMethod.GET)
    public double getSettlementOverAll(@PathVariable Long user1)
    {
        if(user1 == null) return 0;
        return expenseService.getSettlementOverAll(user1);
    }

    @RequestMapping(value = "/api/expenseEqually", method = RequestMethod.POST)
    public boolean addExpenseEqually(@RequestBody expenseDTO expensedto)
    {
        return expenseService.addExpenseEqually(expensedto);
    }

    @RequestMapping(value = "/api/expensePercentage", method = RequestMethod.POST)
    public boolean addExpenseByPercentage(@RequestBody expenseDTO expensedto)
    {
        return expenseService.addExpenseByPercentage(expensedto);
    }

    @RequestMapping(value = "/api/expenseMoney", method = RequestMethod.POST)
    public boolean addExpenseByMoney(@RequestBody expenseDTO expensedto)
    {
        return expenseService.addExpenseByMoney(expensedto);
    }


}
