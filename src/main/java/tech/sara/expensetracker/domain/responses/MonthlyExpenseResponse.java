package tech.sara.expensetracker.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MonthlyExpenseResponse {
    String userName;
    Double amount;
    Date startDate;
    Date endDate;
}
