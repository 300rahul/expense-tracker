package tech.sara.expensetracker.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tech.sara.expensetracker.domain.enums.ExpenseType;
import tech.sara.expensetracker.jpa.entities.ExpenseEntity;
import tech.sara.expensetracker.jpa.entities.ExpenseLimit;
import tech.sara.expensetracker.jpa.entities.User;
import tech.sara.expensetracker.jpa.repository.ExpenseLimitRepo;
import tech.sara.expensetracker.jpa.repository.ExpenseRepo;
import tech.sara.expensetracker.jpa.repository.UserRepo;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Controller
public class ExpenseHelper {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepo userRepo;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    ExpenseLimitRepo expenseLimitRepo;
    public Double roundUptoTwoDecimalPlace(Double number){
        String result = new DecimalFormat("0.00").format(number);
        return Double.parseDouble(result);
    }

    public boolean validateUser(String userName, Long expenseId) {
        Optional<User> optionalUser = userRepo.findByPhoneNumber(userName);
        Optional<ExpenseEntity> optionalExpenseEntity = expenseRepo.findById(Math.toIntExact(expenseId));
        if(optionalExpenseEntity != null && optionalUser != null && optionalExpenseEntity.isPresent() && optionalUser.get().getId() == optionalExpenseEntity.get().getUserId()){
            return true;
        }
        return false;
    }

    public Double getUsersDailyExpensesForMonth(Long userId){
        //Total monthly expense via daily expense
        List<ExpenseEntity> dailyExpensesForCurrentMonth = expenseRepo.findDailyExpensesOfCurrentMonth(userId);
        Double dailyExpenseSumForTheMonth = 0.0;
        for(ExpenseEntity expenseEntity:dailyExpensesForCurrentMonth){
            dailyExpenseSumForTheMonth += expenseEntity.getAmount();
        }
        logger.info("dailyExpenseSumForTheMonth {}", dailyExpenseSumForTheMonth);
        return dailyExpenseSumForTheMonth;
    }

    public Double getUsersTodaysExpense(Long userId){
        //Total monthly expense via daily expense
        List<ExpenseEntity> dailyExpensesForCurrentMonth = expenseRepo.findDailyExpensesOfTheDay(userId);
        Double dailyExpenseSumForTheMonth = 0.0;
        for(ExpenseEntity expenseEntity:dailyExpensesForCurrentMonth){
            dailyExpenseSumForTheMonth += expenseEntity.getAmount();
        }
        logger.info("today's expense is {}", dailyExpenseSumForTheMonth);
        return dailyExpenseSumForTheMonth;
    }

    public Double getUsersTodaysExpenseForACategory(Long userId, String category){
        //Total monthly expense via daily expense
        List<ExpenseEntity> dailyExpensesForCurrentMonth = expenseRepo.findDailyExpensesOfCurrentMonthForACategory(userId, category);
        Double dailyExpenseSumForTheMonth = 0.0;
        for(ExpenseEntity expenseEntity:dailyExpensesForCurrentMonth){
            dailyExpenseSumForTheMonth += expenseEntity.getAmount();
        }
        logger.info("today's expense for {} category is {}",category, dailyExpenseSumForTheMonth);
        return dailyExpenseSumForTheMonth;
    }

    public Double getUsersMonthlyExpense(Long userId){
        //Total monthly expense via daily expense
        Double dailyExpenseSumForTheMonth = getUsersDailyExpensesForMonth(userId);
        //Total monthly expense via monthly set expenses
        List<ExpenseEntity> monthlyExpensesForCurrentMonth = expenseRepo.findMonthlyExpenseForCurrentMonth(userId);
        Double monthlyExpenseSumForTheMonth = 0.0;
        for(ExpenseEntity expenseEntity:monthlyExpensesForCurrentMonth){
            monthlyExpenseSumForTheMonth += expenseEntity.getAmount();
        }
        logger.info("monthlyExpenseSumForTheMonth {}", monthlyExpenseSumForTheMonth);
        return dailyExpenseSumForTheMonth + monthlyExpenseSumForTheMonth;
    }

    public boolean currentMontlyExpenseExceedTheMonthlySlary(Long userId, Double amount) {
        Optional<User> user = userRepo.findById(userId);
        if(user != null && user.isPresent() && user.get().getMonthlyIncome()  != null && user.get().getMonthlyIncome() < amount){
            return true;
        }
        return false;
    }

    public boolean dailyExpenseExceedTheLimit(Long userId, Double amount) {
        Double dailyExpenseOfTheDay = getUsersTodaysExpense(userId);
        logger.info("Today's expenses of the user {} is {}", userId, dailyExpenseOfTheDay);
        ExpenseLimit expenseLimit = expenseLimitRepo.findByUserIdAndType(userId, ExpenseType.DAILY.name());
        if(expenseLimit != null && expenseLimit.getAmount() <= dailyExpenseOfTheDay + amount){
            return true;
        }
        return false;
    }

    public boolean monthlyExpenseExceedTheLimit(Long userId, Double amount) {
        Double totalMonthlyExpense = getUsersMonthlyExpense(userId);
        logger.info("This month total expenses of the user {} is {}", userId, totalMonthlyExpense);
        ExpenseLimit expenseLimit = expenseLimitRepo.findByUserIdAndType(userId, ExpenseType.MONTHLY.name());
        if(expenseLimit != null && expenseLimit.getAmount() <= totalMonthlyExpense + amount){
            return true;
        }
        return false;
    }

    public boolean dailyExpenseExceedTheLimitForParticularCategory(Long userId, Double amount, String category) {
        Double dailyExpenseOfTheDay = getUsersTodaysExpenseForACategory(userId, category);
        logger.info("Today's expenses of the user {} for {} category is {}", userId, category, dailyExpenseOfTheDay);
        ExpenseLimit expenseLimit = expenseLimitRepo.findByUserIdAndTypeAndCategory(userId, ExpenseType.DAILY.name(), category);
        if(expenseLimit != null && expenseLimit.getAmount() <= dailyExpenseOfTheDay + amount){
            return true;
        }
        return false;
    }
}
