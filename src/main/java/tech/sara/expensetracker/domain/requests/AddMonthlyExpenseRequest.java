package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;

import java.util.Date;

@Component
@Data
public class AddMonthlyExpenseRequest {
    Integer userId;
    Double amount;
    ExpenseCategory expenseCategory;
    Date startDate;
    Date endDate;
}
