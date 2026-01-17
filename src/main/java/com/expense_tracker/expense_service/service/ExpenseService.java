package com.expense_tracker.expense_service.service;

import com.expense_tracker.expense_service.dto.ExpenseDto;
import com.expense_tracker.expense_service.entities.Expense;
import com.expense_tracker.expense_service.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ObjectMapper objectMapper;
    private ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public boolean createExpense(ExpenseDto expenseDto) {
        SetCurrency(expenseDto);
        try{
            expenseRepository.save(objectMapper.convertValue(expenseDto, Expense.class));
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Transactional
    public boolean updateExpense(ExpenseDto expenseDto) {
        Optional<Expense> expenseOpt = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(), expenseDto.getExternalId());
        if(expenseOpt.isEmpty()) return false;
        Expense expense = expenseOpt.get();
        expense.setCurrency(!expenseDto.getCurrency().isBlank() ? expenseDto.getCurrency() : expense.getCurrency());
        expense.setMerchant(!expenseDto.getMerchant().isBlank() ? expenseDto.getCurrency() : expense.getMerchant());
        expense.setAmount(expenseDto.getAmount());
        expenseRepository.save(expense);
        return true;
    }

    public List<ExpenseDto> getExpenses(String userId){
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenses, new TypeReference<List<ExpenseDto>>() {});
    }

    private void SetCurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("INR");
        }
    }

}
