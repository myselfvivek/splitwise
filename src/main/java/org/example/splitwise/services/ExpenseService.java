package org.example.splitwise.services;

import org.example.splitwise.dto.expenseDTO;
import org.example.splitwise.entities.Expense;
import org.example.splitwise.entities.Expense_participate;
import org.example.splitwise.entities.User;
import org.example.splitwise.reposetories.ExpenseRepository;
import org.example.splitwise.reposetories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseParticipateService expenseParticipateService;


    public expenseDTO getExpenseById(Long id)
    {

        Expense expense = expenseRepository.findById(id).orElse(null);

        if(expense == null) return null;

        expenseDTO expensedto = new expenseDTO();

        expensedto.setExpense_id(expense.getExpense_id());
        expensedto.setAmount(expense.getAmount());
        expensedto.setDate(expense.getDate());
        expensedto.setDescription(expense.getDescription());
        expensedto.setGroup_id(expense.getGroup_id().getId());
        expensedto.setPayer_id(expensedto.getPayer_id());

        Map<Long, Double> par_share = new HashMap<>();
        List<Expense_participate> user = expenseParticipateService.findAllExpense_participateByExpenseId(id);

        for(Expense_participate ep: user)
        {
            Long participate_id = ep.getParticipate_id().getId();
            Double share = ep.getShare();

            par_share.put(participate_id, share);
        }
        expensedto.setParticipate_shares(par_share);
        return expensedto;
    }

    public expenseDTO getExpenseByUserAndExpense(Long userId, Long expenseId)
    {
        Expense expense =
    }
    // getting expenses between any two users
    private double getSettlementByUserIds(Long user1, Long user2)
    {
        double asPayer = expenseRepository.getSettlementByUserIds(user1, user2);
        double asOwer = expenseRepository.getSettlementByUserIds(user2, user1);

        return (asPayer - asOwer);
    }



    private boolean addExpenseEqually(expenseDTO expense)
    {
        Expense newExpense = new Expense();

        newExpense.setExpense_id(expense.getExpense_id());
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        newExpense.setDate(expense.getDate());


        Long group_id = expense.getGroup_id();
        Map<Long, Double> participate_shares = expense.getParticipate_shares();
        if(group_id != null)
        {
            List<User> users = groupService.findUserByGroupId(group_id);

            List<Long> users_id = new ArrayList<>();
            for(User user: users)
            {
                users_id.add(user.getId());
            }

            if(!users_id.containsAll(participate_shares.keySet())) return false;
        }

        expenseRepository.save(newExpense);

        int total_participate = participate_shares.size() + 1;

        double shared_amount = expense.getAmount()/total_participate;


        for(Long participate_id: participate_shares.keySet())
        {
            Expense_participate newExpense_participate = new Expense_participate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));
            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }
        return true;
    }

    private boolean addExpenseByPercentage(expenseDTO expense)
    {
        Expense newExpense = new Expense();

        newExpense.setExpense_id(expense.getExpense_id());
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        newExpense.setDate(expense.getDate());


        Long group_id = expense.getGroup_id();
        Map<Long, Double> participate_shares = expense.getParticipate_shares();
        if(group_id != null)
        {
            List<User> users = groupService.findUserByGroupId(group_id);

            List<Long> users_id = new ArrayList<>();
            for(User user: users)
            {
                users_id.add(user.getId());
            }

            if(!users_id.containsAll(participate_shares.keySet())) return false;
        }

        expenseRepository.save(newExpense);

        double amount = expense.getAmount();

        for(Long participate_id: participate_shares.keySet())
        {
            Expense_participate newExpense_participate = new Expense_participate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));

            double shared_amount = participate_shares.get(participate_id) * amount / 100 ;

            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }
        return true;
    }

    private boolean addExpenseByMoney(expenseDTO expense)
    {
        Expense newExpense = new Expense();

        newExpense.setExpense_id(expense.getExpense_id());
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        newExpense.setDate(expense.getDate());

        Map<Long, Double> participate_shares = expense.getParticipate_shares();


//        double sum = 0;
//
//        for(Long participate_id: participate_shares.keySet())
//        {
//            sum += participate_shares.get(participate_id);
//        }
//
//        if(sum =)


        Long group_id = expense.getGroup_id();

        if(group_id != null)
        {
            List<User> users = groupService.findUserByGroupId(group_id);

            List<Long> users_id = new ArrayList<>();
            for(User user: users)
            {
                users_id.add(user.getId());
            }

            if(!users_id.containsAll(participate_shares.keySet())) return false;
        }

        expenseRepository.save(newExpense);



        for(Long participate_id: participate_shares.keySet())
        {
            Expense_participate newExpense_participate = new Expense_participate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));

            double shared_amount = participate_shares.get(participate_id);

            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }
        return true;

    }




}
