package com.ExpenseTracker.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ExpenseTracker.dto.CategoryExpenseSummary;
import com.ExpenseTracker.dto.ExpenseUserResponse;
import com.ExpenseTracker.entity.TrackerEntity;

@Repository
public interface TrackerRepository extends JpaRepository<TrackerEntity, Integer>{

	List<TrackerEntity> findByTitle(String title);

	List<TrackerEntity> findByCategoryAndTitle(String category, String title);

	Page<TrackerEntity> findByCategory(String category, Pageable pageable);

	Page<TrackerEntity> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

	Page<TrackerEntity> findByAmountGreaterThan(double amount, Pageable pageable);

	Page<TrackerEntity> findByAmountBetween(double minAmount, double maxAmount, Pageable pageable);

	@Query("SELECT e FROM TrackerEntity e WHERE e.category = :category")
	List<TrackerEntity> findByCategory(@Param("category") String category);
	
	@Query("SELECT e FROM TrackerEntity e WHERE LOWER(e.category) = LOWER(:category) AND LOWER(e.title) LIKE LOWER(CONCAT('%',:keyword,'%')) ORDER BY e.amount desc")
	Page<TrackerEntity> findExpensesByCategoryAndKeyword(@Param("category") String category, @Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT e FROM TrackerEntity e JOIN FETCH e.user WHERE LOWER(e.category) = LOWER(:category) AND LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%'))")
	List<TrackerEntity> findByCategory(@Param("category") String category,@Param("title") String title);

	@Query("SELECT e FROM TrackerEntity e WHERE e.amount < :amount AND LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY e.amount")
	Page<TrackerEntity> findByAmountAndKeyword(@Param("amount") double amount,@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT SUM(e.amount) FROM TrackerEntity e WHERE LOWER(e.category) = LOWER(:category)")
	Double getTotalAmountByCategory(@Param("category") String category);

	@Query("SELECT SUM(e.amount) FROM TrackerEntity e")
	Double getTotalExpenses();
	
	@Query("SELECT AVG(e.amount) FROM TrackerEntity e")
	Double getAvgExpenses();
	
	@Query("SELECT MAX(e.amount) FROM TrackerEntity e")
	Double getHighestExpense();
	
	@Query("SELECT MIN(e.amount) FROM TrackerEntity e")
	Double getLowestExpense();
	
	@Query("SELECT COUNT(e) FROM TrackerEntity e")
	Long getExpensesCount();

	@Query("SELECT new com.ExpenseTracker.dto.ExpenseUserResponse(e.title, e.amount, e.user.name) FROM TrackerEntity e")
	Page<ExpenseUserResponse> getAllExpensesAndUsers(Pageable pageable);

	@Query("SELECT new com.ExpenseTracker.dto.CategoryExpenseSummary(e.category, SUM(e.amount)) FROM TrackerEntity e GROUP BY e.category HAVING SUM(e.amount) > :amount")
	List<CategoryExpenseSummary> getCategorySummary(@Param("amount") Double amount);
	
	
}
