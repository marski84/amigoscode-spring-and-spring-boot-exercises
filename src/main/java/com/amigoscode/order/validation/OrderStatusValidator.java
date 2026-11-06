package com.amigoscode.order.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

// TODO: 4 - Implement the ConstraintValidator for @ValidOrderStatus:
//  - Implement ConstraintValidator<ValidOrderStatus, String>
//  - Define a List of valid statuses: PENDING, SHIPPED, DELIVERED, CANCELLED
//  - In isValid(), return true if value is null (let @NotBlank handle null) or if value is in the list
public class OrderStatusValidator implements ConstraintValidator<ValidOrderStatus, String> {

    List<String> validStatuses = List.of("PENDING", "SHIPPED", "DELIVERED", "CANCELLED");

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return validStatuses.stream().anyMatch(s -> s.equals(value)) || value == null;
    }
}
