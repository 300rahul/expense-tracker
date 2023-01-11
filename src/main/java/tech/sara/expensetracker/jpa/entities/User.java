package tech.sara.expensetracker.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_detail")
@Data
public class User {
    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "password")
    String password;

    @Column(name = "monthly_income")
    Double monthlyIncome;

    @Column(name = "created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdTime;

    @Column(name = "updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedTime;
}
