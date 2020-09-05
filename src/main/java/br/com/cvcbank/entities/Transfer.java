package br.com.cvcbank.entities;

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
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal fee;

    private BigDecimal amount;

    @Column(name = "beneficiary_id", nullable = false)
    private Long beneficiaryId;

    @Column(name = "origin_id", nullable = false)
    private Long originId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", nullable = false, updatable = false, insertable = false)
    private Account beneficiary;

    private BigDecimal transferAmount;

    private LocalDateTime scheduledAt = LocalDateTime.now();

    private LocalDateTime transferDate;

    @PrePersist
    public void prePersist() {
        this.setScheduledAt(LocalDateTime.now());
    }
}
