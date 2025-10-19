package com.cheong.agenticai.dto;

import com.cheong.agenticai.validator.MinuteRange;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String slotNo;

    @NotNull
    @FutureOrPresent
    @MinuteRange
    private LocalDateTime startDateTime;

    @NotNull
    @FutureOrPresent
    private LocalDateTime endDateTime;
}
