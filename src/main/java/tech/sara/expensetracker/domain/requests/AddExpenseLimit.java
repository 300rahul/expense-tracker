package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;
import tech.sara.expensetracker.domain.enums.ExpenseType;

import java.util.Date;

@Component
@Data
public class AddExpenseLimit {
    Integer userId;
    ExpenseType expenseType;
    Double amount;
}
