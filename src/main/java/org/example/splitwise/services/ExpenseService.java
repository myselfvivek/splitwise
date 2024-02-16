package org.example.splitwise.services;

import org.example.splitwise.dto.expenseDTO;
import org.example.splitwise.entities.Expense;
import org.example.splitwise.entities.ExpenseParticipate;
import org.example.splitwise.entities.Group;
import org.example.splitwise.entities.User;
import org.example.splitwise.reposetories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ExpenseParticipateService expenseParticipateService;

    public expenseDTO convertExpenseToExpenseDTO(Expense expense)
    {
        expenseDTO expensedto = new expenseDTO();

//        expensedto.setExpense_id(expense.getExpense_id());
        expensedto.setAmount(expense.getAmount());
        expensedto.setDate(expense.getDate());
        expensedto.setDescription(expense.getDescription());
        if(expense.getGroup_id() != null) expensedto.setGroup_id(expense.getGroup_id().getId());
        else expensedto.setGroup_id(null);
        expensedto.setPayer_id(expensedto.getPayer_id());

        Map<Long, Double> par_share = new HashMap<>();
        List<ExpenseParticipate> user = expenseParticipateService.findAllExpense_participateByExpenseId(expense.getExpense_id());

        for(ExpenseParticipate ep: user)
        {
            Long participate_id = ep.getParticipate_id().getId();
            Double share = ep.getShare();

            par_share.put(participate_id, share);
        }
        expensedto.setParticipate_shares(par_share);

        return expensedto;
    }


    public List<expenseDTO> getAllExpenseByUserId(Long user_id)
    {
        System.out.println("he");
        List<Expense> expenseList = expenseRepository.getAllExpenseByUserId(user_id);

        List<expenseDTO> expenseDTOSList = new ArrayList<>();

        for(Expense expense: expenseList)
        {
            expenseDTO expensedto = convertExpenseToExpenseDTO(expense);
            expenseDTOSList.add(expensedto);
        }
        return expenseDTOSList;
    }

    public expenseDTO getExpenseByUserIdExpenseId(Long user_id, Long expense_id)
    {
        System.out.println("hi");
        User user = userService.findUserById(user_id);
        if(user == null) return null;


        Expense expense = expenseRepository.getExpenseByUserIdExpenseId(user_id, expense_id);

        if(expense == null) return null;

        return convertExpenseToExpenseDTO(expense);
    }
    public expenseDTO getExpenseById(Long id)
    {
        Expense expense = expenseRepository.findById(id).orElse(null);

        if(expense == null) return null;

        expenseDTO expensedto = convertExpenseToExpenseDTO(expense);
        return expensedto;
    }

    public List<expenseDTO> getAllExpenseByGroupId(Long group_id, Long user_id)
    {
        User user = userService.findUserById(user_id);
        if(user == null) return null;

        Group group = groupService.findGroupByUserIdGroupId(user_id, group_id);
        if(group == null) return null;


        List<Expense> expenses = expenseRepository.getAllExpenseByGroupId(group_id, user_id);
        List<expenseDTO> expenseDTOS = new ArrayList<>();
        for(Expense expense : expenses)
        {
            expenseDTO expensedto = convertExpenseToExpenseDTO(expense);
            expenseDTOS.add(expensedto);
        }
        return expenseDTOS;
    }

    // getting expenses between any two users
    public double getSettlementByUserIds(Long user1, Long user2)
    {
        User current_user1 = userService.findUserById(user1);
        User current_user2 = userService.findUserById(user2);

        if(current_user1 == null || current_user2 == null) return 0;

        System.out.println("hje");

        double asPayer = expenseRepository.getSettlementByUserIds(user1, user2);
        System.out.println("jh");
        double asOwer = expenseRepository.getSettlementByUserIds(user2, user1);
        System.out.println("hieori");
        return (asPayer - asOwer);
    }

    public double getSettlementByUserAndGroupIds(Long userId1, Long userId2, Long groupId)
    {
        System.out.println("hi");
        User current_user1 = userService.findUserById(userId1);
        User current_user2 = userService.findUserById(userId2);
        System.out.println("hii");
        if(current_user1 == null || current_user2 == null) return 0;

        Group user1Group = groupService.findGroupByUserIdGroupId(userId1, groupId);
        Group user2Group = groupService.findGroupByUserIdGroupId(userId2, groupId);

        System.out.println("hello");
        if(user1Group == null || user2Group == null || user2Group != user1Group) return 0;

        System.out.println("uhuu");
        double asPayer = expenseRepository.getSettlementByUserIdsGroupId(userId1, userId2, groupId);
        double asOwer = expenseRepository.getSettlementByUserIdsGroupId(userId2, userId1, groupId);
        System.out.println("my bad");
        return (asPayer - asOwer);
    }

    public double getSettlementByGroupIds(Long userId1, Long groupId)
    {
        User curr_user = userService.findUserById(userId1);
        if(curr_user == null) return 0;

        Group curr_group = groupService.findGroupByUserIdGroupId(userId1, groupId);
        if(curr_group == null) return 0;

        System.out.println("hi");

        double asPayer = expenseRepository.getSettlementAsPayerInGroup(userId1, groupId);
        System.out.println("vi");
        double asOwer = expenseRepository.getSettlementAsOwerInGroup(userId1, groupId);
        System.out.println("ii");
        return (asPayer - asOwer);

    }

    public double getSettlementOverAll(Long userId1)
    {
        double asPayer = expenseRepository.getSettlementAsPayer(userId1);
        double asOwer = expenseRepository.getSettlementAsOwer(userId1);

        return (asPayer - asOwer);
//    return 0;
    }

    public boolean addExpenseEqually(expenseDTO expense)
    {
        Expense newExpense = new Expense();
        System.out.println("hello");
//        newExpense.setExpense_id(expense.getExpense_id());

        newExpense.setAmount(expense.getAmount());
        System.out.println("1");
        newExpense.setDescription(expense.getDescription());
        System.out.println("2");
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        System.out.println("3");
        if(expense.getGroup_id() != null) newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        else newExpense.setGroup_id(null);
        System.out.println("4");
        newExpense.setDate(new Date());

        System.out.println("ji");
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
        System.out.println("hi");
        expenseRepository.save(newExpense);

        int total_participate = participate_shares.size();

        double shared_amount = expense.getAmount()/total_participate;

        System.out.println("maki");
        for(Long participate_id: participate_shares.keySet())
        {
            ExpenseParticipate newExpense_participate = new ExpenseParticipate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));
            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }
        return true;
    }

    public boolean addExpenseByPercentage(expenseDTO expense)
    {
        Expense newExpense = new Expense();

//        newExpense.setExpense_id(expense.getExpense_id());
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        if(expense.getGroup_id() != null) newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        else newExpense.setGroup_id(null);
        newExpense.setDate(new Date());


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

//        check if total percentage is 100

        double percentage_sum = 0;
        for(Long participate_id: participate_shares.keySet())
        {
            percentage_sum += participate_shares.get(participate_id);
        }
        if(percentage_sum != 100) return false;


        expenseRepository.save(newExpense);

        double amount = expense.getAmount();

        for(Long participate_id: participate_shares.keySet())
        {
            ExpenseParticipate newExpense_participate = new ExpenseParticipate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));

            double shared_amount = participate_shares.get(participate_id) * amount / 100 ;

            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }

        return true;
    }

    public boolean addExpenseByMoney(expenseDTO expense)
    {
        Expense newExpense = new Expense();

//        newExpense.setExpense_id(expense.getExpense_id());
        newExpense.setAmount(expense.getAmount());
        newExpense.setDescription(expense.getDescription());
        newExpense.setPayer_id(userService.findUserById(expense.getPayer_id()));
        if(expense.getGroup_id() != null) newExpense.setGroup_id(groupService.findGroupById(expense.getGroup_id()));
        else newExpense.setGroup_id(null);
        newExpense.setDate(new Date());

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


        double money_sum = 0;
        for(Long participate_id: participate_shares.keySet())
        {
            money_sum += participate_shares.get(participate_id);
        }
        if(money_sum != expense.getAmount()) return false;


        expenseRepository.save(newExpense);



        for(Long participate_id: participate_shares.keySet())
        {
            ExpenseParticipate newExpense_participate = new ExpenseParticipate();

            newExpense_participate.setExpense_id(newExpense);
            newExpense_participate.setParticipate_id(userService.findUserById(participate_id));

            double shared_amount = participate_shares.get(participate_id);

            newExpense_participate.setShare(shared_amount);

            expenseParticipateService.addExpense_participation(newExpense_participate);
        }
        return true;

    }




}
