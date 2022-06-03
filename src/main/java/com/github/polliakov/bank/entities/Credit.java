package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "credit")
public class Credit {
    public Credit() { }

    public Credit(Long id,
                  BigDecimal sumLimit,
                  BigDecimal rate,
                  Collection<CreditOffer> offers,
                  Collection<Bank> banks) {
        this.id = id;
        this.sumLimit = sumLimit;
        this.rate = rate;
        this.offers = offers;
        this.banks = banks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal sumLimit;

    @Column(nullable = false)
    private BigDecimal rate;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER)
    private Collection<CreditOffer> offers;

    @ManyToMany(mappedBy = "credits", fetch = FetchType.LAZY)
    private Collection<Bank> banks;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSumLimit() {
        return sumLimit;
    }

    public void setSumLimit(BigDecimal sumLimit) {
        this.sumLimit = sumLimit;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Collection<CreditOffer> getOffers() {
        return offers;
    }

    public void setOffers(Collection<CreditOffer> offers) {
        this.offers = offers;
    }

    public Collection<Bank> getBanks() {
        return banks;
    }

    public void setBanks(Collection<Bank> banks) {
        this.banks = banks;
    }
    //endregion
}
