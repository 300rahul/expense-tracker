package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DeleteRequest {
    Integer userId;
    Integer expenseId;
}
