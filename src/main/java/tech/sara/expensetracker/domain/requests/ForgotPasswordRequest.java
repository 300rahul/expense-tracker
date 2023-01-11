package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Component
@Data
public class ForgotPasswordRequest {
    @Pattern(regexp = "\\d{10}")
    String phoneNumber;
    @Pattern(regexp = "\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"\n")
    String password;
    String otp;
}
