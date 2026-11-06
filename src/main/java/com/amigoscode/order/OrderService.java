package com.amigoscode.order;

import com.amigoscode.order.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    Optional<Order> getOrderById(final Long id) {
        return orderRepository.findById(id);
    }

    List<Order> getOrdersByStatus(final String status) {
        return getAllOrders().stream().filter(o -> o.getStatus().equals(status)).toList();
    }

    Order createOrder(final Order order) {
        return orderRepository.save(order);
    }

    void updateOrder(final Order order) {
        Order orderToUpdate = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderNotFoundException(order.getId()));

        orderToUpdate.setDescription(order.getDescription());
        orderToUpdate.setCustomerEmail(order.getCustomerEmail());
        orderToUpdate.setStatus(order.getStatus());
        orderToUpdate.setInternalNotes(order.getInternalNotes());
        orderToUpdate.setTotalAmount(order.getTotalAmount());

        orderRepository.save(orderToUpdate);
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
