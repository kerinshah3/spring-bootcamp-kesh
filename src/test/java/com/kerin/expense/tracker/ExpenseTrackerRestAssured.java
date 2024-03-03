package com.kerin.expense.tracker;

import com.google.gson.Gson;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.kerin.expense.tracker.model.Transaction;
import com.kerin.expense.tracker.service.ExpenseService;
import com.kerin.expense.tracker.utils.ClientMetaData;
import com.kerin.expense.tracker.repository.ExpenseDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.apache.http.HttpStatus.SC_OK;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExpenseTrackerRestAssured {

    @Value("${local.server.port}")
    int port;

    @Value("${server.servlet.context-path}")
    String serverContextPath;

    @MockBean
    ExpenseDaoImpl widgetRepository;

    @MockBean
    ExpenseService expenseService;

    String baseUrl;

    @BeforeAll
    public void setup() {
        RestAssured.port = port;
        baseUrl = "http://localhost:" + port + serverContextPath + "/rest/expense";

    }
    @Test
    public void testPostWidget() throws Exception {
        BDDMockito.given(expenseService.save(ArgumentMatchers.anyObject())).willReturn(Transaction.builder()
                .type("income")
                .description("salary")
                .amount(1000D)
                .build());


        Gson gson = new Gson();
        String expenseJson = gson.toJson(Transaction.builder()
                .type("income")
                .description("salary")
                .amount(1000D)
                .build());

        ClientMetaData clientMetaData = gson.fromJson("{\"appOrg\":\"com.banking\",\"language\":\"en\",\"appCode\":\"ABC0\",\"appVersion\":\"3.2\",\"physicalLocationId\":\"123\",\"assetId\":\"laptop-123\",\"legacyId\":\"123\",\"requestUniqueId\":\"123e4567-e89b-12d3-a456-556642440000\"}", ClientMetaData.class);

        System.out.println("baseUrl = " + baseUrl);
        Response response =

                RestAssured
                        .given()
                        .header("client-metadata", clientMetaData)
                        .contentType(ContentType.JSON)
                        .request()
                        .body(expenseJson)
                        .when()
                        .post(baseUrl)
                        .then()
                        .statusCode(SC_OK)
                        .contentType(ContentType.JSON)
//						.assertThat()
//						.body(Matchers.notNull())
                        .extract().response();
        String responseString = response.asString();
        System.out.println("responseString:" + responseString);

        Transaction transaction = gson.fromJson(responseString, Transaction.class);
        Assertions.assertThat(StringUtils.equalsIgnoreCase(transaction.getDescription(), "salary"));
        Assertions.assertThat(StringUtils.equalsIgnoreCase(transaction.getType(), "income"));
    }
}
