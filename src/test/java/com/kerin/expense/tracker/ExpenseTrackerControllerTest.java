package com.kerin.expense.tracker;

import com.google.gson.Gson;
import com.kerin.expense.tracker.dto.TransactionDto;
import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.service.ExpenseService;
import com.kerin.expense.tracker.repository.ExpenseDaoImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
public class ExpenseTrackerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExpenseDaoImpl expenseDaoImpl;

    @MockBean
    ExpenseService expenseService;


    @Test
    public void testGetExpense() throws Exception {

        BDDMockito.given(expenseService.findById(1L))
                .willReturn(new Transaction(1L,"dinner",-20D,"expense"));

        mockMvc.perform(MockMvcRequestBuilders.get("/rest/expense/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("type").value("expense"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("dinner"))
                .andExpect(MockMvcResultMatchers.jsonPath("amount").value(-20D));
    }

    @Test
    public void testSaveExpense() throws Exception {
        BDDMockito.given(expenseService.save(ArgumentMatchers.anyObject())).willReturn(new Transaction(2L,"dinner",-20D,"expense"));

        Gson gson = new Gson();
        String requestBody = gson.toJson(TransactionDto.builder().amount(-20D).description("dinner").type("expense").build());
        mockMvc.perform(MockMvcRequestBuilders.post("/rest/expense").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("type").value("expense"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("dinner"))
                .andExpect(MockMvcResultMatchers.jsonPath("amount").value(-20D));
    }

}
