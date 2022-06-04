package com.github.polliakov.bank.entities;

import javax.persistence.*;
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

    @Column(nullable = false)
    private BigDecimal sumLimit;

    @Column(nullable = false)
    private Double rate;

    @OneToMany(mappedBy = "creditEntity", fetch = FetchType.EAGER)
    private Collection<CreditOfferEntity> offerEntities;

    @ManyToMany(mappedBy = "creditEntities", fetch = FetchType.LAZY)
    private Collection<BankEntity> bankEntities;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Collection<CreditOfferEntity> getOfferEntities() {
        return offerEntities;
    }

    public void setOfferEntities(Collection<CreditOfferEntity> offerEntities) {
        this.offerEntities = offerEntities;
    }

    public Collection<BankEntity> getBanks() {
        return bankEntities;
    }

    public void setBanks(Collection<BankEntity> bankEntities) {
        this.bankEntities = bankEntities;
    }
    //endregion
}
