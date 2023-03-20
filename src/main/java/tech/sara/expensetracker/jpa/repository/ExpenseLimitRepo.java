package tech.sara.expensetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.sara.expensetracker.jpa.entities.ExpenseLimit;

public interface ExpenseLimitRepo extends JpaRepository<ExpenseLimit, Integer> {
    ExpenseLimit findByUserIdAndType(Long userId, String type);
    ExpenseLimit findByUserIdAndTypeAndCategory(Long userId, String type, String category);
}
