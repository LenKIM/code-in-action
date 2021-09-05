package com.example.springmvcwithitemservice.domain.item;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * FAST 빠른 배송
 * NORMAL 보통
 * SLOW 느린 배송
 */
@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName;
}
