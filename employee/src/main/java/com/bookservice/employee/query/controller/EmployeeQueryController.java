package com.bookservice.employee.query.controller;

import com.bookservice.commonservice.model.EmployeeResponseCommonModel;
import com.bookservice.employee.query.model.EmployeeRespsonseModel;
import com.bookservice.employee.query.queries.GetAllEmployeeQuery;
import com.bookservice.commonservice.queries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
@Hidden
@Slf4j
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
        log.info("getAllEmployee");
        return queryGateway.query(new GetAllEmployeeQuery(isDisciplined),
                ResponseTypes.multipleInstancesOf(EmployeeRespsonseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseCommonModel getDetailEmployee(@PathVariable String employeeId) {
        return queryGateway.query(new GetDetailEmployeeQuery(employeeId),
                ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
    }
}
