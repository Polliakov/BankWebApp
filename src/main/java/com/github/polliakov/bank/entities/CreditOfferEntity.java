package com.github.polliakov.bank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "credit_offers")
public class CreditOfferEntity {
    public CreditOfferEntity() { }

    public CreditOfferEntity(Long id,
                             BigDecimal total,
                             Collection<CreditPaymentEntity> payments,
                             CreditEntity creditEntity,
                             ClientEntity clientEntity) {
        this.id = id;
        this.total = total;
        this.payments = payments;
        this.creditEntity = creditEntity;
        this.clientEntity = clientEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "creditOfferEntity", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Collection<CreditPaymentEntity> payments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_id")
    private CreditEntity creditEntity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private ClientEntity clientEntity;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException();
        this.total = total;
    }

    @JsonIgnore
    public Collection<CreditPaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(Collection<CreditPaymentEntity> payments) {
        this.payments = payments;
    }

    public CreditEntity getCredit() {
        return creditEntity;
    }

    public void setCredit(CreditEntity creditEntity) {
        this.creditEntity = creditEntity;
    }

    public ClientEntity getClient() {
        return clientEntity;
    }

    public void setClient(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }
    //endregion
}
