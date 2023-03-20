package tech.sara.expensetracker.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.sara.expensetracker.jpa.entities.ExpenseEntity;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<ExpenseEntity, Integer> {
    //This func will return all the daily expense entries for the user for current month
    @Query("SELECT e FROM ExpenseEntity e WHERE DATE_TRUNC('month', e.createdTime) = DATE_TRUNC('month', CURRENT_DATE) and e.userId = :userId and e.type = 'DAILY'")
    List<ExpenseEntity> findDailyExpensesOfCurrentMonth(@Param("userId") Long userId);

    @Query("SELECT e FROM ExpenseEntity e WHERE DATE_TRUNC('day', e.createdTime) = DATE_TRUNC('day', CURRENT_DATE) and e.userId = :userId and e.type = 'DAILY'")
    List<ExpenseEntity> findDailyExpensesOfTheDay(@Param("userId") Long userId);

    @Query("SELECT e FROM ExpenseEntity e WHERE DATE_TRUNC('month', e.createdTime) = DATE_TRUNC('month', CURRENT_DATE) and e.userId = :userId and e.type = 'DAILY' and e.category = :category")
    List<ExpenseEntity> findDailyExpensesOfCurrentMonthForACategory(@Param("userId") Long userId, @Param("userId") String category);

    //This func will return all the monthly expense entries for the user which are applicable on current month
    @Query("SELECT e FROM ExpenseEntity e WHERE  DATE_TRUNC('month', CURRENT_DATE) >= DATE_TRUNC('month', e.startTime) and DATE_TRUNC('month', CURRENT_DATE) <= DATE_TRUNC('month', e.endTime) and e.userId = :userId and e.type = 'MONTHLY'")
    List<ExpenseEntity> findMonthlyExpenseForCurrentMonth(@Param("userId") Long userId);
}
