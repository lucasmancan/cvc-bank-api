package br.com.app.services.impl;

import br.com.app.entities.Transfer;
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
    void testTransferForTheNext41Days() {
        var transfer = mockTransferForTheNext41Days();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.02").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    @Test
    void testTransferGreaterThan100000() {
        var transfer = mockTransferGreaterThan100000();

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.02").multiply(transfer.getTransferAmount());

        assertEquals(0, fee.compareTo(expectedValue));
    }

    /**
     * Transfer greater than 100000 has low priority compared to same day transfer
     */
    @Test
    void testTransferGreaterThan100000InTheSameDay() {
        var transfer = mockTransferGreaterThan100000();
        transfer.setTransferDate(LocalDateTime.now());

        BigDecimal fee = feeService.calculateByTransfer(transfer);
        BigDecimal expectedValue = new BigDecimal("0.03").multiply(transfer.getTransferAmount()).add(new BigDecimal(3));

        assertEquals(0, fee.compareTo(expectedValue));
    }

    /**
     * Same day transfer scenario
     *
     * @return
     */
    public Transfer mockSameDayTransfer() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now());
        return transfer;
    }

    /**
     * Between 1 - 10 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferForTheNext5Days() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(5));
        return transfer;
    }

    /**
     * Between 11 - 20 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferForTheNext15Days() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(15));
        return transfer;
    }

    /**
     * Between 21 - 30 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferForTheNext25Days() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(25));
        return transfer;
    }

    /**
     * Between 31 - 40 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferForTheNext35Days() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(35));
        return transfer;
    }

    /**
     * Greater than 40 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferForTheNext41Days() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(1000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(41));
        return transfer;
    }

    /**
     * Greater than 100000 Days Scenario
     *
     * @return
     */
    public Transfer mockTransferGreaterThan100000() {
        Transfer transfer = new Transfer();
        transfer.setTransferAmount(new BigDecimal(100000));
        transfer.setScheduledAt(LocalDateTime.now());
        transfer.setTransferDate(LocalDateTime.now().plusDays(41));
        return transfer;
    }
}