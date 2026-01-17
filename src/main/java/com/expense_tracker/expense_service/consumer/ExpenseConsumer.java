package com.expense_tracker.expense_service.consumer;

import com.expense_tracker.expense_service.dto.ExpenseDto;
import com.expense_tracker.expense_service.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ExpenseConsumer {

    private final ExpenseService expenseService;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto expenseDto){
        System.out.println("Received message: " + expenseDto);
        try{
            expenseService.createExpense(expenseDto);
        } catch (Exception ex) {
            System.out.println("Error in listening data: " + ex.getMessage());
        }
    }
}
