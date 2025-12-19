package com.devoops.rentalbrain.customer.customerlist.command.service;

import com.devoops.rentalbrain.common.codegenerator.CodeGenerator;
import com.devoops.rentalbrain.common.codegenerator.CodeType;
import com.devoops.rentalbrain.customer.channel.command.repository.ChannelRepository;
import com.devoops.rentalbrain.customer.customerlist.command.dto.CustomerlistCommandDTO;
import com.devoops.rentalbrain.customer.customerlist.command.entity.CustomerlistCommandEntity;
import com.devoops.rentalbrain.customer.customerlist.command.repository.CustomerlistCommandRepository;
import com.devoops.rentalbrain.customer.segment.command.repository.SegmentCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerlistCommandServiceImpl implements CustomerlistCommandService {

    private final CustomerlistCommandRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CodeGenerator codeGenerator;

    @Override
    public Long registerCustomer(CustomerlistCommandDTO dto) {
        CustomerlistCommandEntity customer = modelMapper.map(dto, CustomerlistCommandEntity.class);

        // 1. 코드 생성 (엔티티에 customerCode 필드가 있으므로 정상 작동)
        customer.setCustomerCode(codeGenerator.generate(CodeType.CUSTOMER));

        // 2. 외래키 기본값 설정 (수정됨: 객체 대신 ID 값을 직접 확인하고 설정)
        if (customer.getSegmentId() == null) {
            customer.setSegmentId(1L); // '잠재 고객' ID 강제 주입
        }

        if (customer.getChannelId() == null) {
            customer.setChannelId(6L); // '기타' 채널 ID 강제 주입
        }

        customer.setIsDeleted("N");
        CustomerlistCommandEntity saved = customerRepository.save(customer);
        return saved.getId();
    }

    @Override
    public void updateCustomer(Long id, CustomerlistCommandDTO dto) {
        CustomerlistCommandEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 고객입니다."));

        // DTO의 값으로 엔티티 업데이트 (null이 아닌 경우만)
        if (dto.getName() != null) customer.setName(dto.getName());
        if (dto.getInCharge() != null) customer.setInCharge(dto.getInCharge());
        if (dto.getDept() != null) customer.setDept(dto.getDept());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getEmail() != null) customer.setEmail(dto.getEmail());
        if (dto.getAddr() != null) customer.setAddr(dto.getAddr());
        if (dto.getMemo() != null) customer.setMemo(dto.getMemo());

        // 필요시 세그먼트/채널 변경 로직 추가 가능
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerlistCommandEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 고객입니다."));
        customer.setIsDeleted("Y"); // Soft Delete
        log.info("고객 삭제(Soft Delete) 완료: ID={}", id);
    }

    @Override
    public void restoreCustomer(Long id) {
        CustomerlistCommandEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 고객입니다."));
        customer.setIsDeleted("N"); // 복구
        log.info("고객 복구(Restore) 완료: ID={}", id);
    }
}