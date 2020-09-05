package br.com.app.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer extends AbstractEntity {
    private BigDecimal fee;

    private BigDecimal amount;

    @Column(name = "beneficiary_id", nullable = false)
    private Long beneficiaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", nullable = false, updatable = false, insertable = false)
    private Account beneficiary;

    private BigDecimal transferAmount;

    private LocalDateTime scheduledAt;

    private LocalDateTime transferDate;
}
