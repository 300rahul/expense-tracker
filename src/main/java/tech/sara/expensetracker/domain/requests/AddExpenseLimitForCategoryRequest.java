package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import tech.sara.expensetracker.domain.enums.ExpenseCategory;
import tech.sara.expensetracker.domain.enums.ExpenseType;

import javax.persistence.criteria.CriteriaBuilder;
import java.nio.DoubleBuffer;
import java.util.Date;

@Component
@Data
public class AddExpenseLimitForCategoryRequest {
    String expenseType;
    String expenseCategory;
    Double amount;
}
