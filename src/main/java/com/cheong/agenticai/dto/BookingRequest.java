package com.cheong.agenticai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    private String userId;

    private String slotNo;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
