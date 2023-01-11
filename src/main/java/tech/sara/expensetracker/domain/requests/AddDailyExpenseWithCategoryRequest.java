package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;

import javax.validation.constraints.Pattern;

@Component
@Data
public class AddDailyExpenseWithCategoryRequest {
    Integer userId;
    Double amount;
    ExpenseCategory category;
}
