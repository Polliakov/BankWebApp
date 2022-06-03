package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Entity
@Table(name = "credit_offers")
public class CreditOffer {
    public CreditOffer() { }

    public CreditOffer(Long id,
                       BigDecimal total,
                       Collection<CreditPayment> payments,
                       Credit credit,
                       Client client) {
        this.id = id;
        this.total = total;
        this.payments = payments;
        this.credit = credit;
        this.client = client;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "creditOffer", fetch = FetchType.EAGER)
    private Collection<CreditPayment> payments;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

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
        this.total = total;
    }

    public Collection<CreditPayment> getPayments() {
        return payments;
    }

    public void setPayments(Collection<CreditPayment> payments) {
        this.payments = payments;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    //endregion
}
