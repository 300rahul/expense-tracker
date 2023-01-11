package tech.sara.expensetracker.domain.responses;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FailedResponse {
    String message;
    Integer code;
}
