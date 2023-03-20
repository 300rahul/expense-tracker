package tech.sara.expensetracker.controllers;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.sara.expensetracker.domain.enums.ErrorMessage;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;
import tech.sara.expensetracker.domain.enums.ExpenseType;
import tech.sara.expensetracker.domain.requests.*;
import tech.sara.expensetracker.domain.responses.FailedResponse;
import tech.sara.expensetracker.jpa.entities.ExpenseEntity;
import tech.sara.expensetracker.jpa.entities.ExpenseLimit;
import tech.sara.expensetracker.jpa.entities.User;
import tech.sara.expensetracker.jpa.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ExpenseServiceImpl implements ExpenseService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserRepo userRepo;

    @Autowired
    ExpenseRepo expenseRepo;

    @Autowired
    ExpenseHelper expenseHelper;

    @Autowired
    ExpenseLimitRepo expenseLimitRepo;
    public FailedResponse updateMonthlyIncome(Double amount, String userName) {
        FailedResponse failedResponse = new FailedResponse();
        try{
            logger.info("Updating monthly income for user {}", userName);
            Optional<User> optionalUser = userRepo.findByPhoneNumber(userName);
            //Commenting becausing this thing is already validating while access token validation
//            if(optionalUser == null || !optionalUser.isPresent()){
//                failedResponse.setMessage(ErrorMessage.USER_DOES_NOT_EXIST.getMessage());
//                failedResponse.setCode(ErrorMessage.USER_DOES_NOT_EXIST.getCode());
//                return new ResponseEntity<>(failedResponse, HttpStatus.BAD_REQUEST);
//            }
            User user = optionalUser.get();
            Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(amount);
            user.setMonthlyIncome(amountRoundedUptoTwoDecimalPlace);
            userRepo.save(user);
            return null;
        }catch (Exception e){
            logger.error("Failed to update monthly income for user name: {}", userName, e);
            failedResponse.setMessage(ErrorMessage.FAILED_TO_UPDATE_MONTHLY_INCOME.getMessage());
            failedResponse.setCode(ErrorMessage.FAILED_TO_UPDATE_MONTHLY_INCOME.getCode());
            return failedResponse;
        }
    }

    @Override
    public FailedResponse addDailyExpense(AddDailyExpenseRequest addDailyExpenseRequest, String userName) {
        FailedResponse failedResponse = new FailedResponse();
        try {
            logger.info("Adding daily expense for user {}", userName);
            if (!EnumUtils.isValidEnum(ExpenseCategory.class, addDailyExpenseRequest.getCategory())) {
                logger.error("Request validation failed for AddDailyExpenseRequest");
                failedResponse.setMessage(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage());
                failedResponse.setCode(ErrorMessage.REQUEST_VALIDATION_FAIL.getCode());
                return failedResponse;
            }
            Optional<User> optionalUser = userRepo.findByPhoneNumber(userName);
            ExpenseEntity expenseEntity = new ExpenseEntity();
            expenseEntity.setCategory(addDailyExpenseRequest.getCategory());
            Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(addDailyExpenseRequest.getAmount());
            //Checking for daily expense limit
            //Todo: add notification
            if (expenseHelper.dailyExpenseExceedTheLimit(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace)) {
                logger.info("Daily expense exceeds the limit for the user {}", userName);
                //add notification
            }

            //Checking for monthly income
            //Todo: add notification
            if (expenseHelper.currentMontlyExpenseExceedTheMonthlySlary(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace)) {
                logger.info("Monthly expense exceeds the monthly income for the user {}", userName);
                //add notification
            }

            //Checking for monthly expense limit
            //Todo: add notification
            if (expenseHelper.monthlyExpenseExceedTheLimit(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace)) {
                logger.info("Expense exceeds the monthly expense limit for the user {}", userName);
                //add notification
            }
            //Todo: Add one more validation to check the monthly expense limit for the category
            if(expenseHelper.dailyExpenseExceedTheLimitForParticularCategory(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace, addDailyExpenseRequest.getCategory())){
                logger.info("Daily expense for {} category exceeds the limit for the user {}", addDailyExpenseRequest.getCategory(), userName);
            }
            expenseEntity.setAmount(amountRoundedUptoTwoDecimalPlace);
            expenseEntity.setUserId(optionalUser.get().getId());
            expenseEntity.setType(ExpenseType.DAILY.name());
            expenseEntity.setCreatedTime(new Date());
            expenseEntity.setUpdatedTime(new Date());
            expenseRepo.save(expenseEntity);
            return null;
        } catch (Exception e) {
            logger.error("Failed to add daily expense for user name: {}", userName, e);
            failedResponse.setMessage(ErrorMessage.FAILED_TO_ADD_DAILY_EXPENSE.getMessage());
            failedResponse.setCode(ErrorMessage.FAILED_TO_ADD_DAILY_EXPENSE.getCode());
            return failedResponse;
        }
    }

    @Override
    public FailedResponse deleteExpense(Long expenseId, UserDetails userDetails) {
        FailedResponse failedResponse = new FailedResponse();
        try{
            logger.info("Delete daily expense for user {}", userDetails.getUsername());
            Optional<ExpenseEntity> expenseEntity = expenseRepo.findById(Math.toIntExact(expenseId));
            if(expenseEntity != null && expenseEntity.isPresent()){
                Optional<User> user = userRepo.findByPhoneNumber(userDetails.getUsername());
                if(expenseEntity.get().getUserId() != user.get().getId()){
                    logger.error("User id validation failed");
                    failedResponse.setCode(ErrorMessage.REQUEST_VALIDATION_FAIL.getCode());
                    failedResponse.setMessage(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage());
                    return failedResponse;
                }
                expenseRepo.delete(expenseEntity.get());
            }
            return null;
        } catch (Exception e){
            logger.error("Failed to delete the expense.", e);
            failedResponse.setCode(ErrorMessage.FAILED_PROCESS_REQUEST.getCode());
            failedResponse.setMessage(ErrorMessage.FAILED_PROCESS_REQUEST.getMessage());
            return failedResponse;
        }
    }

    @Override
    public FailedResponse addMonthlyExpense(MonthlyExpenseRequest monthlyExpenseRequest, String  userName) {
        FailedResponse failedResponse = new FailedResponse();

        logger.info("Add monthly expense for user {}", userName);
        try{
            if(!EnumUtils.isValidEnum(ExpenseCategory.class, monthlyExpenseRequest.getExpenseCategory())){
                logger.error("Request validation failed for AddDailyExpenseRequest");
                failedResponse.setMessage(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage());
                failedResponse.setCode(ErrorMessage.REQUEST_VALIDATION_FAIL.getCode());
                return failedResponse;
            }
            Optional<User> optionalUser = userRepo.findByPhoneNumber(userName);
            Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(monthlyExpenseRequest.getAmount());

            //Checking for monthly income
            //Todo: add notification
            if (expenseHelper.currentMontlyExpenseExceedTheMonthlySlary(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace)) {
                logger.info("Monthly expense exceeds the monthly income for the user {}", userName);
                //add notification
            }

            //Checking for monthly expense limit
            //Todo: add notification
            if (expenseHelper.monthlyExpenseExceedTheLimit(optionalUser.get().getId(), amountRoundedUptoTwoDecimalPlace)) {
                logger.info("Expense exceeds the monthly expense limit for the user {}", userName);
                //add notification
            }
            ExpenseEntity monthlyExpense = new ExpenseEntity();
            monthlyExpense.setCategory(monthlyExpenseRequest.getExpenseCategory());
            monthlyExpense.setAmount(amountRoundedUptoTwoDecimalPlace);
            monthlyExpense.setType(ExpenseType.MONTHLY.name());
            monthlyExpense.setStartTime(monthlyExpenseRequest.getStartDate());
            monthlyExpense.setEndTime(monthlyExpenseRequest.getEndDate());
            monthlyExpense.setUserId(optionalUser.get().getId());
            monthlyExpense.setCreatedTime(new Date());
            monthlyExpense.setUpdatedTime(new Date());
            expenseRepo.save(monthlyExpense);
            return null;
        } catch (Exception e){
            logger.error("Failed to add monthly expense for user: {}", userName, e);
            failedResponse.setCode(ErrorMessage.FAILED_PROCESS_REQUEST.getCode());
            failedResponse.setMessage(ErrorMessage.FAILED_PROCESS_REQUEST.getMessage());
            return failedResponse;
        }
    }

//    @Override
//    public FailedResponse updateMonthlyExpense(MonthlyExpenseRequest monthlyExpenseRequest, String userName, Long expenseId) {
//        FailedResponse failedResponse = new FailedResponse();
//        logger.info("Update monthly expense for user {}", userName);
//        try{
//            if(!expenseHelper.validateUser(userName, expenseId)){
//                logger.error("Failed to update monthly expense for user: {}", userName);
//                failedResponse.setCode(ErrorMessage.INVALID_USER.getCode());
//                failedResponse.setMessage(ErrorMessage.INVALID_USER.getMessage());
//                return failedResponse;
//            }
//            Optional<ExpenseEntity> optionalExpenseEntity = expenseRepo.findById(Math.toIntExact(expenseId));
//            ExpenseEntity expenseEntity = optionalExpenseEntity.get();
//            if(monthlyExpenseRequest.getAmount() != null){
//                Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(monthlyExpenseRequest.getAmount());
//                expenseEntity.setAmount(amountRoundedUptoTwoDecimalPlace);
//            }
//            if(!monthlyExpenseRequest.getExpenseCategory().isEmpty()) expenseEntity.setCategory(monthlyExpenseRequest.getExpenseCategory());
//            if(monthlyExpenseRequest.getStartDate() != null) expenseEntity.setStartTime(monthlyExpenseRequest.getStartDate());
//            if(monthlyExpenseRequest.getEndDate() != null) expenseEntity.setEndTime(monthlyExpenseRequest.getEndDate());
//            logger.info("Updated expense entity is {}", expenseEntity);
//            expenseRepo.save(expenseEntity);
//            return null;
//        } catch (Exception e){
//            logger.error("Failed to update monthly expense for user: {}", userName, e);
//            failedResponse.setCode(ErrorMessage.FAILED_PROCESS_REQUEST.getCode());
//            failedResponse.setMessage(ErrorMessage.FAILED_PROCESS_REQUEST.getMessage());
//            return failedResponse;
//        }
//    }

    @Override
    public FailedResponse addExpenseLimit(AddExpenseLimitForCategoryRequest addExpenseLimitRequest, String userName) {
        FailedResponse failedResponse = new FailedResponse();

        logger.info("Add expense limit for user {}", userName);
        try{
            if((addExpenseLimitRequest.getExpenseCategory() != null && !EnumUtils.isValidEnum(ExpenseCategory.class, addExpenseLimitRequest.getExpenseCategory())) ||
                    !EnumUtils.isValidEnum(ExpenseType.class, addExpenseLimitRequest.getExpenseType())){
                logger.error("Request validation failed for AddDailyExpenseRequest");
                failedResponse.setMessage(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage());
                failedResponse.setCode(ErrorMessage.REQUEST_VALIDATION_FAIL.getCode());
                return failedResponse;
            }
            Double amountRoundedUptoTwoDecimalPlace = expenseHelper.roundUptoTwoDecimalPlace(addExpenseLimitRequest.getAmount());
            Optional<User> optionalUser = userRepo.findByPhoneNumber(userName);
            ExpenseLimit expenseLimit = new ExpenseLimit();
            if(addExpenseLimitRequest.getExpenseCategory() != null) expenseLimit.setCategory(addExpenseLimitRequest.getExpenseCategory());
            expenseLimit.setAmount(amountRoundedUptoTwoDecimalPlace);
            expenseLimit.setType(addExpenseLimitRequest.getExpenseType());
            expenseLimit.setUserId(optionalUser.get().getId());
            expenseLimit.setCreatedTime(new Date());
            expenseLimit.setUpdatedTime(new Date());
            expenseLimitRepo.save(expenseLimit);
            return null;
        } catch (Exception e){
            logger.error("Failed to add monthly expense for user: {}", userName, e);
            failedResponse.setCode(ErrorMessage.FAILED_PROCESS_REQUEST.getCode());
            failedResponse.setMessage(ErrorMessage.FAILED_PROCESS_REQUEST.getMessage());
            return failedResponse;
        }
    }

    @Override
    public FailedResponse deleteExpenseLimit(Long expenseLimitId, String username) {
        FailedResponse failedResponse = new FailedResponse();
        try{
            logger.info("Delete expense limit for user {}", username);
            Optional<ExpenseLimit> expenseEntity = expenseLimitRepo.findById(Math.toIntExact(expenseLimitId));
            if(expenseEntity != null && expenseEntity.isPresent()){
                Optional<User> user = userRepo.findByPhoneNumber(username);
                if(expenseEntity.get().getUserId() != user.get().getId()){
                    logger.error("User id validation failed");
                    failedResponse.setCode(ErrorMessage.REQUEST_VALIDATION_FAIL.getCode());
                    failedResponse.setMessage(ErrorMessage.REQUEST_VALIDATION_FAIL.getMessage());
                    return failedResponse;
                }
                expenseLimitRepo.delete(expenseEntity.get());
            }
            return null;
        } catch (Exception e){
            logger.error("Failed to delete expense limit.", e);
            failedResponse.setCode(ErrorMessage.FAILED_PROCESS_REQUEST.getCode());
            failedResponse.setMessage(ErrorMessage.FAILED_PROCESS_REQUEST.getMessage());
            return failedResponse;
        }
    }
}
