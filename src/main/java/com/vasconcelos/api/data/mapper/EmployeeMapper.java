package com.vasconcelos.api.data.mapper;

import com.vasconcelos.api.data.vo.v1.EmployeeVO;
import com.vasconcelos.api.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeVO employeeVO);

    EmployeeVO toVo(Employee employee);
}
