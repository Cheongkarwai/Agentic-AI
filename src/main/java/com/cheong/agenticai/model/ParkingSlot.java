package com.cheong.agenticai.model;

import com.cheong.agenticai.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("parking_slots")
public class ParkingSlot implements BaseEntity {

    @Id
    private String id;

    @Column("slot_no")
    private String slotNo;

}
