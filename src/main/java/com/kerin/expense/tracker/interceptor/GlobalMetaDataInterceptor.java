package com.kerin.expense.tracker.interceptor;

import com.google.gson.Gson;
import com.kerin.expense.tracker.constant.ApplicationConstant;
import com.kerin.expense.tracker.exception.InvalidDataException;
import com.kerin.expense.tracker.utils.ClientMetaData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class GlobalMetaDataInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("entered preHandle");
        entryLogTime(request);

        if(request.getHeader(ApplicationConstant.HEADER) != null ){
            String metaDataJsonString = request.getHeader(ApplicationConstant.HEADER);
            decodeMetaData(metaDataJsonString,request,response);
        }else {
            InvalidDataException invalidDataException = new InvalidDataException();
            invalidDataException.getErrorMap().put(ApplicationConstant.HEADER,
                    "client_metadata request header cannot be missing or have blank value");
            throw invalidDataException;
        }

        log.info("exited preHandle");
        return true;

    }

    private void decodeMetaData(String metaDataJsonString, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();

        ClientMetaData clientMetaData = new ClientMetaData();

        try {
            clientMetaData = gson.fromJson(metaDataJsonString,ClientMetaData.class);
        }catch (Exception ex){
            InvalidDataException invalidDataException = new InvalidDataException();
            invalidDataException.getErrorMap().put(ApplicationConstant.HEADER,
                    "Error While Parsing MetaData =" + metaDataJsonString);
            throw invalidDataException;
        }
        verifyMetaData(clientMetaData);
    }

    private void verifyMetaData(ClientMetaData clientMetaData) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ClientMetaData>> constraintViolationSet = validator.validate(clientMetaData);
        if(constraintViolationSet != null && !constraintViolationSet.isEmpty())
        {
            InvalidDataException invalidDataException = new InvalidDataException();
            for (ConstraintViolation<ClientMetaData> constraintViolations : constraintViolationSet){
                String pathError = constraintViolations.getPropertyPath().toString();
                String message = constraintViolations.getMessage();
                invalidDataException.getErrorMap().put(pathError,message);
            }
            throw invalidDataException;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Entering Completion");
        exitLogTime(request);
        log.info("Exiting Completion");
    }

    private void entryLogTime(HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("URL : " + request.getRequestURL() + " Entry at " + startTime);
        request.setAttribute("startTime",startTime);
    }

    private void exitLogTime(HttpServletRequest request) {
        log.info("URL : " + request.getRequestURL() + " Exit at " + System.currentTimeMillis() );
    }
}