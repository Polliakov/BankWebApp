package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "credit_payments")
public class CreditPaymentEntity {
    public CreditPaymentEntity() { }

    public CreditPaymentEntity(Long id,
                               Date date,
                               BigDecimal total,
                               BigDecimal bodyPayment,
                               BigDecimal ratePayment,
                               CreditOfferEntity creditOfferEntity) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.bodyPayment = bodyPayment;
        this.ratePayment = ratePayment;
        this.creditOfferEntity = creditOfferEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private BigDecimal bodyPayment;

    @Column(nullable = false)
    private BigDecimal ratePayment;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_offer_id")
    private CreditOfferEntity creditOfferEntity;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getBodyPayment() {
        return bodyPayment;
    }

    public void setBodyPayment(BigDecimal bodyPayment) {
        this.bodyPayment = bodyPayment;
    }

    public BigDecimal getRatePayment() {
        return ratePayment;
    }

    public void setRatePayment(BigDecimal ratePayment) {
        this.ratePayment = ratePayment;
    }

    public CreditOfferEntity getCreditOffer() {
        return creditOfferEntity;
    }

    public void setCreditOffer(CreditOfferEntity creditOfferEntity) {
        this.creditOfferEntity = creditOfferEntity;
    }
    //endregion
}
