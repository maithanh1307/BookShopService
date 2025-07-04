package com.bookservice.employee.query.controller;

import com.bookservice.employee.query.model.EmployeeRespsonseModel;
import com.bookservice.employee.query.queries.GetAllEmployeeQuery;
import com.bookservice.employee.query.queries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
@Hidden
public class EmployeeQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @Operation (
            summary = "Get list employees",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse (
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse (
                            description = "Authorized / Invalid token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping
    public List<EmployeeRespsonseModel> getAllEmployee(@RequestParam(required = false, defaultValue = "false") Boolean isDisciplined) {
        return queryGateway.query(new GetAllEmployeeQuery(isDisciplined),
                ResponseTypes.multipleInstancesOf(EmployeeRespsonseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeRespsonseModel getDetailEmployee(@PathVariable String employeeId) {
        return queryGateway.query(new GetDetailEmployeeQuery(employeeId),
                ResponseTypes.instanceOf(EmployeeRespsonseModel.class)).join();
    }
}
