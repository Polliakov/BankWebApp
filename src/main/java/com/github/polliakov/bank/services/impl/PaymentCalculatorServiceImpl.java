package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.services.PaymentCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

@Service
public class PaymentCalculatorServiceImpl implements PaymentCalculatorService {
    public List<CreditPaymentEntity> calculatePayments(CreditOfferEntity creditOffer, int monthsCount) {
        // region Validation
        if (creditOffer == null)
            throw new NullPointerException("creditOffer");
        if (creditOffer.getCredit() == null)
            throw new NullPointerException("creditOffer.credit");
        if (creditOffer.getCredit().getRate() <= 0)
            throw new IllegalArgumentException("creditOffer.credit.rate");
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
            var monthRate = calculateMonthRate(rate, calendar);
            var payment = calculatePayment(remainingDebt, total, monthRate, calendar);
            remainingDebt = remainingDebt.subtract(payment.getBodyPayment());
            payments.add(payment);
        }
        calendar.add(Calendar.MONTH, 1);
        var monthRate = calculateMonthRate(rate, calendar);
        var lastPayment = remainingDebt.add(remainingDebt.multiply(monthRate));
        var payment = calculatePayment(remainingDebt, lastPayment, monthRate, calendar);
        payments.add(payment);

        return payments;
    }

    private CreditPaymentEntity calculatePayment(BigDecimal remainingDebt,
                                                 BigDecimal total,
                                                 BigDecimal monthRate,
                                                 Calendar calendar){
        var ratePayment = remainingDebt.multiply(monthRate);
        var bodyPayment = total.subtract(ratePayment);

        var payment = new CreditPaymentEntity();
        payment.setDate(calendar.getTime());
        payment.setTotal(total.setScale(2, RoundingMode.HALF_EVEN));
        payment.setBodyPayment(bodyPayment.setScale(2, RoundingMode.HALF_EVEN));
        payment.setRatePayment(ratePayment.setScale(2, RoundingMode.HALF_EVEN));
        return payment;
    }

    private BigDecimal calculateMonthRate(double rate, Calendar calendar) {
        double daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        double daysInYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        return BigDecimal.valueOf(Math.pow(1 + rate, daysInMonth / daysInYear) - 1);
    }

    private BigDecimal calculateAnnuityRatio(double rate, int monthsCount) {
        double monthRateAvg = Math.pow(1 + rate, 1.0 / 12.0) - 1;
        var mrByPeriod = BigDecimal.valueOf(Math.pow(1 + monthRateAvg, monthsCount));
        return BigDecimal.valueOf(monthRateAvg).multiply(mrByPeriod)
                .divide(mrByPeriod.subtract(BigDecimal.ONE), RoundingMode.HALF_EVEN);
    }
}
