package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.services.CreditPaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class CreditPaymentServiceImpl implements CreditPaymentService {
    @Override
    public List<CreditPaymentEntity> calculatePayments(CreditOfferEntity creditOffer, int monthsCount) {
        // region Validation
        if (creditOffer == null)
            throw new NullPointerException("creditOffer");
        if (creditOffer.getClient() == null)
            throw new NullPointerException("creditOffer.client");
        if (creditOffer.getCredit() == null)
            throw new NullPointerException("creditOffer.credit");
        if (monthsCount < 1)
            throw new IllegalArgumentException("monthsCount");
        // endregion

        var calendar = new GregorianCalendar();
        var remainingDebt = creditOffer.getTotal();
        double rate = creditOffer.getCredit().getRate();
        var total = remainingDebt.multiply(calculateAnnuityRatio(rate, monthsCount));
        var payments = new LinkedList<CreditPaymentEntity>();

        for (int i = 0; i < monthsCount - 1; i++) {
            calendar.add(Calendar.MONTH, 1);
            var payment = calculatePayment(remainingDebt, total, rate, calendar);
            remainingDebt = remainingDebt.subtract(total);
            payments.add(payment);
        }
        calendar.add(Calendar.MONTH, 1);
        var payment = calculatePayment(remainingDebt, remainingDebt, rate, calendar);
        payments.add(payment);

        return payments;
    }

    private CreditPaymentEntity calculatePayment(BigDecimal remainingDebt,
                                                 BigDecimal total,
                                                 double rate,
                                                 Calendar calendar){
        var monthRate = calculateMonthRate(rate, calendar);
        var ratePayment = remainingDebt.multiply(monthRate);
        var bodyPayment = total.subtract(ratePayment);

        var payment = new CreditPaymentEntity();
        payment.setDate(calendar.getTime());
        payment.setTotal(total);
        payment.setBodyPayment(bodyPayment);
        payment.setRatePayment(ratePayment);
        return payment;
    }

    private BigDecimal calculateMonthRate(double rate, Calendar calendar) {
        double daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        double daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        return BigDecimal.valueOf(Math.pow(1 + rate, daysInMonth / daysInYear) - 1);
    }

    private BigDecimal calculateAnnuityRatio(double rate, int monthsCount) {
        double monthRateAvg = Math.pow(1 + rate, 1.0 / 12.0);
        var mrByPeriod = BigDecimal.valueOf(Math.pow(1 + monthRateAvg, monthsCount));
        return BigDecimal.valueOf(monthRateAvg).multiply(mrByPeriod)
                .divide(mrByPeriod.subtract(BigDecimal.ONE), RoundingMode.HALF_EVEN);
    }
}
