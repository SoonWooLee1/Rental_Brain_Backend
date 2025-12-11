package com.devoops.rentalbrain.customer.customerlist.command.controller;

import com.devoops.rentalbrain.customer.customerlist.command.dto.CustomerCommandDTO;
import com.devoops.rentalbrain.customer.customerlist.command.service.CustomerCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Slf4j
public class CustomerCommandController {

    private final CustomerCommandService commandService;

    // 고객 등록
    @PostMapping("/insertCustomer")
    public ResponseEntity<Long> register(@RequestBody CustomerCommandDTO dto) {
        Long savedId = commandService.registerCustomer(dto);
        log.info("고객 등록 완료 ID: {}", savedId);
        return ResponseEntity.ok(savedId);
    }

    // 고객 수정
    @PutMapping("/updateCustomer/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody CustomerCommandDTO dto) {
        commandService.updateCustomer(id, dto);
        log.info("고객 수정 완료 ID: {}", id);
        return ResponseEntity.ok("고객 정보가 수정되었습니다.");
    }

    // 고객 삭제 (Soft Delete)
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        commandService.deleteCustomer(id);
        log.info("고객 삭제(Soft Delete) 완료 ID: {}", id);
        return ResponseEntity.ok("삭제되었습니다.");
    }
}