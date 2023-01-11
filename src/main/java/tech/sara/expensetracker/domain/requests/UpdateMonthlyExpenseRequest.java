package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;

import java.util.Date;

@Component
@Data
public class UpdateMonthlyExpenseRequest {
    Integer userId;
    Double amount;
    Integer expenseId;
    Date startDate;
    Date endDate;
}
