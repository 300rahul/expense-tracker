package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Data
public class MonthlyExpenseRequest {
    Double amount;
    String expenseCategory;
    Date startDate;
    Date endDate;
}
