package com.amigoscode.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(String status);

    List<Order> findByCustomerEmail(String email);

    @Query("SELECT o FROM Order o WHERE o.totalAmount > :amount")
    List<Order> findByTotalAmountGreaterThan(@Param("amount") double amount);
}
