package com.cheong.agenticai.model;

import com.cheong.agenticai.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("booking_slots")
public class BookingSlot implements BaseEntity {

    @Id
    private String id;

    @Column("user_id")
    private String userId;

    @Column("parking_slot_id")
    private String parkingSlotId;

    @Column("start_time")
    private LocalDateTime startDateTime;

    @Column("end_time")
    private LocalDateTime endDateTime;

    @Column("transaction_id")
    private String transactionId;

    private Status status;

    public enum Status {
        PENDING, CONFIRMED, CANCELLED;
    }
}
