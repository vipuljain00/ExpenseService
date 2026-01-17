package com.expense_tracker.expense_service.controller;

import com.expense_tracker.expense_service.dto.ExpenseDto;
import com.expense_tracker.expense_service.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping("expense/v1/")
    public ResponseEntity<?> getExpenses(@PathParam("user_id") @NonNull String userId){
        try {
            List<ExpenseDto>expensesDtoList = expenseService.getExpenses(userId);
            return new ResponseEntity<>(expensesDtoList, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Couldn't fetch Expenses.", HttpStatus.NOT_FOUND);
        }
    }

}
