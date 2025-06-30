package com.bookservice.employee.command.controller;

import com.bookservice.employee.command.command.CreateEmployeeCommand;
import com.bookservice.employee.command.data.EmployeeRepository;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bookservice.employee.command.model.CreateEmployeeModel;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(),
                                        model.getFirstName(), model.getLastName(), model.getKin(),
                                false);
        return commandGateway.sendAndWait(command);
    }
}
