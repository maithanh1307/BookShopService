package com.bookservice.employee.query.projection;

import com.bookservice.employee.command.data.Employee;
import com.bookservice.employee.command.data.EmployeeRepository;
import com.bookservice.employee.query.model.EmployeeRespsonseModel;
import com.bookservice.employee.query.queries.GetAllEmployeeQuery;
import com.bookservice.employee.query.queries.GetDetailEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProjection {
    @Autowired
    private EmployeeRepository employeeRepository;

    @QueryHandler
    public List<EmployeeRespsonseModel> handle(GetAllEmployeeQuery getAllEmployeeQuery) {
        List<Employee> listEmployee = employeeRepository.findAllByIsDisciplined(getAllEmployeeQuery.getIsDisciplined());

        return listEmployee.stream().map(employee -> {
            EmployeeRespsonseModel employeeRespsonseModel = new EmployeeRespsonseModel();
            BeanUtils.copyProperties(employee, employeeRespsonseModel);
            return employeeRespsonseModel;
        }).toList();
    }


    @QueryHandler
    public EmployeeRespsonseModel handle(GetDetailEmployeeQuery getDetailEmployeeQuery) throws Exception {
        Employee employee = employeeRepository.findById(getDetailEmployeeQuery.getId()).orElseThrow(() -> new Exception("Employee not found"));
        EmployeeRespsonseModel employeeRespsonseModel = new EmployeeRespsonseModel();
        BeanUtils.copyProperties(employee, employeeRespsonseModel);
        return employeeRespsonseModel;
    }


}
