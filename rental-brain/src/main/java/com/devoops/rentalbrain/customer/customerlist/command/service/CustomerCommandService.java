package com.devoops.rentalbrain.customer.customerlist.command.service;

import com.devoops.rentalbrain.customer.customerlist.command.dto.CustomerCommandDTO;
import com.devoops.rentalbrain.customer.customerlist.command.entity.Customer;
import com.devoops.rentalbrain.customer.customerlist.command.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerCommandService {

    private final CustomerRepository customerRepository;

    // 등록
    public Long registerCustomer(CustomerCommandDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setInCharge(dto.getInCharge());
        customer.setDept(dto.getDept());
        customer.setCallNum(dto.getCallNum());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        customer.setBusinessNum(dto.getBusinessNum());
        customer.setAddr(dto.getAddr());
        customer.setMemo(dto.getMemo());
        customer.setStar(dto.getStar());
        customer.setChannelId(dto.getChannelId());
        customer.setSegmentId(dto.getSegmentId());
        // @PrePersist에 의해 isDeleted='N' 자동 설정됨

        return customerRepository.save(customer).getId();
    }

    // 수정
    public void updateCustomer(Long id, CustomerCommandDTO dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다. ID=" + id));

        if(dto.getName() != null) customer.setName(dto.getName());
        if(dto.getInCharge() != null) customer.setInCharge(dto.getInCharge());
        if(dto.getDept() != null) customer.setDept(dto.getDept());
        if(dto.getCallNum() != null) customer.setCallNum(dto.getCallNum());
        if(dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if(dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if(dto.getBusinessNum() != null) customer.setBusinessNum(dto.getBusinessNum());
        if(dto.getAddr() != null) customer.setAddr(dto.getAddr());
        if(dto.getMemo() != null) customer.setMemo(dto.getMemo());
        if(dto.getStar() != null) customer.setStar(dto.getStar());
        if(dto.getChannelId() != null) customer.setChannelId(dto.getChannelId());
        if(dto.getSegmentId() != null) customer.setSegmentId(dto.getSegmentId());
    }

    // 삭제 (Soft Delete)
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다. ID=" + id));

        customer.setIsDeleted("Y"); // 상태만 변경
        log.info("고객 삭제(Soft Delete) 완료: ID={}", id);
    }
}