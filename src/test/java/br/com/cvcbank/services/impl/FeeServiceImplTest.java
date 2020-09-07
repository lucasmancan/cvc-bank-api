package br.com.cvcbank.services.impl;

import br.com.cvcbank.dtos.TransferSummary;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = {FeeServiceImpl.class})
class FeeServiceImplTest {

    @Autowired
    FeeServiceImpl feeService;

    @Test
    void testSameDayTransfer() {
        var transfer = mockSameDayTransfer();

        BigDecimal fee = feeService.calculateByTransfer(mockSameDayTransfer());
        BigDecimal expectedValue = new BigDecimal("0.03").multiply(transfer.getTransferAmount()).add(new BigDecimal(3));

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferForTheNext5Days() {
        var transfer = mockTransferForTheNext5Days();
        long differenceInDays = ChronoUnit.DAYS.between(transfer.getScheduledAt(), transfer.getTransferDate());

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("12").multiply(new BigDecimal(differenceInDays));

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferForTheNext15Days() {
        var transfer = mockTransferForTheNext15Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.08").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferForTheNext25Days() {
        var transfer = mockTransferForTheNext25Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.06").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferForTheNext35Days() {
        var transfer = mockTransferForTheNext35Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.04").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferForTheNext41DaysAndValueGreaterThan100000() {
        var transfer = mockTransferGreaterThan100000AndForTheNext41Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.02").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    @Disabled("Disabled until gap in requirements is solved!")
    void testTransferForTheNext41Days() {
        var transfer = mockTransferForTheNext41Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.02").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    /**
     * Transfer greater than 100000 has low priority compared to same day transfer
     */
    @Test
    void testTransferGreaterThan100000InTheSameDay() {

        var transfer = new TransferSummary(new BigDecimal(100000), LocalDateTime.now(), LocalDateTime.now());

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.03").multiply(transfer.getTransferAmount()).add(new BigDecimal(3));

        assertEquals(0, fee.compareTo(expectedValue));
    }

    /**
     * Same day transfer scenario
     *
     * @return
     */
    public TransferSummary mockSameDayTransfer() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now());
    }

    /**
     * Between 1 - 10 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferForTheNext5Days() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now().plusDays(5));
    }

    /**
     * Between 11 - 20 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferForTheNext15Days() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now().plusDays(15));
    }

    /**
     * Between 21 - 30 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferForTheNext25Days() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now().plusDays(25));
    }

    /**
     * Between 31 - 40 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferForTheNext35Days() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now().plusDays(35));
    }

    /**
     * Greater than 40 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferForTheNext41Days() {
        return new TransferSummary(new BigDecimal(1000), LocalDateTime.now(), LocalDateTime.now().plusDays(41));
    }

    /**
     * Greater than 100000 Days Scenario
     *
     * @return
     */
    public TransferSummary mockTransferGreaterThan100000AndForTheNext41Days() {
        return new TransferSummary(new BigDecimal(100000), LocalDateTime.now(), LocalDateTime.now().plusDays(41));
    }
}