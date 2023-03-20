package tech.sara.expensetracker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import tech.sara.expensetracker.domain.requests.*;
import tech.sara.expensetracker.domain.responses.FailedResponse;

public interface ExpenseService {
    FailedResponse updateMonthlyIncome(Double amount, String userId);
    FailedResponse addDailyExpense(AddDailyExpenseRequest addDailyExpenseRequest, String userId);

    FailedResponse deleteExpense(Long expenseId, UserDetails userDetails);

    FailedResponse addMonthlyExpense(MonthlyExpenseRequest monthlyExpenseRequest, String userName);

//    FailedResponse updateMonthlyExpense(MonthlyExpenseRequest monthlyExpenseRequest, String userName, Long expenseId);

    FailedResponse addExpenseLimit(AddExpenseLimitForCategoryRequest addExpenseLimitRequest, String userName);

    FailedResponse deleteExpenseLimit(Long expenseLimitId, String username);
}
