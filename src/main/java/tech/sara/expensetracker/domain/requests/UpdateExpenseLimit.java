package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseType;

@Component
@Data
public class UpdateExpenseLimit {
    Integer userId;
    Integer expenseId;
    Double amount;
}
