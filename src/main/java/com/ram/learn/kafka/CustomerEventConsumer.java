
package com.ram.learn.kafka;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ram.learn.exception.RecordNotFoundException;
import com.ram.learn.model.EmployeeEntity;
import com.ram.learn.service.EmployeeService;

@Component
public class CustomerEventConsumer {

    private final KafkaTemplate kafkaTemplate;

    @Autowired
    EmployeeService service;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public CustomerEventConsumer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "customer_loader")
    public void processMessage(String payLoad)
            throws RecordNotFoundException, JsonParseException, JsonMappingException, IOException {

        EmployeeEntity employee = mapper.readValue(payLoad, EmployeeEntity.class);

        System.out.println("Employee data from topic :: " + employee.toString());
        service.createOrUpdateEmployee(employee);

    }

}
