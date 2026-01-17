package com.expense_tracker.expense_service.consumer;

import com.expense_tracker.expense_service.dto.ExpenseDto;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ExpenseDeserializer implements Deserializer<ExpenseDto> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public ExpenseDto deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        ExpenseDto expenseDto = null;
        try {
            expenseDto = mapper.readValue(bytes, ExpenseDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return expenseDto;
        }
    }

    @Override
    public void close() {}
}
