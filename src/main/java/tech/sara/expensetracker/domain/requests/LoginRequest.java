package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Data
public class LoginRequest {
    @Pattern(regexp = "\\d{10}")
    String phoneNumber;
    String password;
}
