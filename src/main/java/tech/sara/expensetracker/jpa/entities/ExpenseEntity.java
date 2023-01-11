package tech.sara.expensetracker.jpa.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Data
public class ExpenseEntity {
    @Id
    @Column(name = "id")
    private Long Id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "category")
    String category;

    @Column(name = "type")
    String type;

    @Column(name = "start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endTime;

    @Column(name = "created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdTime;

    @Column(name = "updated_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedTime;
}
