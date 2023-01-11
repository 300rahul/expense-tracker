package tech.sara.expensetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.sara.expensetracker.jpa.entities.ExpenseEntity;

public interface ExpenseRepo extends JpaRepository<ExpenseEntity, Integer> {
}
