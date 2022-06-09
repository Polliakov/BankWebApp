package com.github.polliakov.bank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collection;


@Entity
@Table(name = "credit")
public class CreditEntity {
    public CreditEntity() { }

    public CreditEntity(Long id,
                        BigDecimal sumLimit,
                        Double rate,
                        Collection<CreditOfferEntity> offers,
                        Collection<BankEntity> bankEntities) {
        this.id = id;
        this.sumLimit = sumLimit;
        this.rate = rate;
        this.offerEntities = offers;
        this.bankEntities = bankEntities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private BigDecimal sumLimit;

    @NotNull
    @Column(nullable = false)
    private Double rate;

    @OneToMany(mappedBy = "creditEntity", fetch = FetchType.EAGER)
    private Collection<CreditOfferEntity> offerEntities;

    @ManyToMany(mappedBy = "creditEntities", fetch = FetchType.LAZY)
    private Collection<BankEntity> bankEntities;

    @PreRemove
    private void removeCreditFromBanks() {
        for (var b : bankEntities)
            b.getCredits().remove(this);
    }

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
        if (sumLimit.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException();
        this.sumLimit = sumLimit;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        if (rate < 0)
            throw new IllegalArgumentException();
        this.rate = rate;
    }

    @JsonIgnore
    public Collection<CreditOfferEntity> getOfferEntities() {
        return offerEntities;
    }

    public void setOfferEntities(Collection<CreditOfferEntity> offerEntities) {
        this.offerEntities = offerEntities;
    }

    @JsonIgnore
    public Collection<BankEntity> getBanks() {
        return bankEntities;
    }

    public void setBanks(Collection<BankEntity> bankEntities) {
        this.bankEntities = bankEntities;
    }
    //endregion
}
