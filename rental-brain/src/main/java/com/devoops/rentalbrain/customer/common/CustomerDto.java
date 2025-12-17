package com.devoops.rentalbrain.customer.common;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private Long id;
    private String customerCode;
    private String name;
    private String inCharge;
    private String dept;
    private String callNum; // DDL: call_num
    private String phone;
    private String email;
    private String businessNum;
    private String addr;
    private String memo;
    private Integer star;   // [수정] DDL: star INTEGER
    private String isDeleted;

    // 조인된 데이터
    private Long channelId;
    private String channelName;
    private Long segmentId;
    private String segmentName;

    // 통계 데이터 (서브쿼리 결과)
    private Long totalTransactionAmount;
    private int contractCount;

    // 날짜 필드 (LocalDateTime 사용)
    private LocalDateTime recentRentalDate;
    private LocalDateTime firstContractDate;
    private LocalDateTime lastTransaction;
}