package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;

@Component
@Data
public class UpdateDailyExpenseWithCategoryRequest {
    Integer userId;
    Integer expenseId;
    Double amount;
}
