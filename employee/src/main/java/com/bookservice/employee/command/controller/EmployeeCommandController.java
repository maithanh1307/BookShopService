package com.bookservice.employee.command.controller;

import com.bookservice.employee.command.command.CreateEmployeeCommand;
import com.bookservice.employee.command.command.DeleteEmployeeCommand;
import com.bookservice.employee.command.command.UpdateEmployeeCommand;
import com.bookservice.employee.command.data.EmployeeRepository;
import com.bookservice.employee.command.model.UpdateEmployeeModel;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bookservice.employee.command.model.CreateEmployeeModel;
import com.bookservice.employee.command.model.UpdateEmployeeModel;
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

    @PutMapping("/{employeeId}")
    public String updateEmployee(@PathVariable String employeeId, @Valid @RequestBody UpdateEmployeeModel model) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, model.getFirstName(),
                                                                    model.getLastName(), model.getKin(),
                                                                    model.getIsDisciplined());
        return commandGateway.sendAndWait(command);
    }

    @Hidden
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        return commandGateway.sendAndWait(command);
    }

}
