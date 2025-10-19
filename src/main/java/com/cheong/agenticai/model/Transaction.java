package com.cheong.agenticai.model;

import com.cheong.agenticai.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("transactions")
public class Transaction implements BaseEntity {

    @Id
    private String id;

    @Column("user_id")
    private String userId;

    private BigDecimal amount;

    @Column("transaction_date")
    private LocalDateTime transactionDate;

    private Status status;

    public enum Status {
        SUCCESS, FAILED, PENDING, INITIATED
    }
}
