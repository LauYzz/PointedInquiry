package com.pointedInquery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wyx20
 * @version 1.0
 * @title OrderCreateDto
 * @description
 * @create 2023/12/13 14:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDto {
    String customer_id;
    String expert_id;
    String topic_id;
    String appoint_time;
    Integer price;

    public void setUserId(String number) {
    }

    public void setProductId(String number) {
    }

    public void setQuantity(int i) {
    }

    public short getUserId() {
        return 0;
    }

    public short getProductId() {
        return 0;
    }

    public short getQuantity() {
        return 0;
    }
}
