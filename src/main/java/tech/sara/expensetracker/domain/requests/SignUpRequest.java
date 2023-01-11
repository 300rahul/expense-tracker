package tech.sara.expensetracker.domain.requests;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
@Data
public class SignUpRequest {
    @NotEmpty
    @Size(min = 2, max = 50, message = "The length of first name must be between 2 and 50 characters.")
    String firstName;

    @NotEmpty
    @Size(min = 2, max = 50, message = "The length of last name must be between 2 and 50 characters.")
    String lastName;

    @NotEmpty(message = "Phone number is required.")
    @Pattern(regexp = "\\d{10}")
    String phoneNumber;

    @Pattern(regexp = "\"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\"\n")
    String password;

    @NotNull
    Double monthlyIncome;
}
