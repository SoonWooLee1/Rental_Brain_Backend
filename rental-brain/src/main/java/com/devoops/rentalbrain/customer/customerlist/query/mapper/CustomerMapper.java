package com.devoops.rentalbrain.customer.customerlist.query.mapper;

import com.devoops.rentalbrain.customer.common.CustomerDto;
import com.devoops.rentalbrain.customer.customerlist.query.dto.CustomerSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CustomerMapper {
    // 검색 조건(DTO)을 받아 목록 조회
    List<CustomerDto> selectCustomerList(CustomerSearchDTO searchDto);

    // 전체 개수 조회
    long selectCustomerCount(CustomerSearchDTO searchDto);

    // 상세 조회
    CustomerDto selectCustomerById(@Param("id") Long id);
}