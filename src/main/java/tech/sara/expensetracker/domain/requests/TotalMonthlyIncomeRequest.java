package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Data
public class TotalMonthlyIncomeRequest {
    Integer userId;
    Double amount;
}
