package com.cheong.agenticai.dto;


import java.time.LocalDateTime;

public record BookingSlotDTO(String id, String parkingSlotId, String userId, LocalDateTime startTime, LocalDateTime endTime) {
}
