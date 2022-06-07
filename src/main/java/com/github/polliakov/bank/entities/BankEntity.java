package com.github.polliakov.bank.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "banks")
public class BankEntity {
    public BankEntity() { }

    public BankEntity(Long id, Collection<ClientEntity> clientEntities, Collection<CreditEntity> creditEntities) {
        this.id = id;
        this.clientEntities = clientEntities;
        this.creditEntities = creditEntities;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "clients_in_banks",
            joinColumns = @JoinColumn(name = "bank_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "client_id", nullable = false)
    )
    private Collection<ClientEntity> clientEntities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "credits_in_banks",
            joinColumns = @JoinColumn(name = "bank_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "credit_id", nullable = false)
    )
    private Collection<CreditEntity> creditEntities;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Collection<ClientEntity> getClients() {
        return clientEntities;
    }

    public void setClients(Collection<ClientEntity> clientEntities) {
        this.clientEntities = clientEntities;
    }

    @JsonIgnore
    public Collection<CreditEntity> getCredits() {
        return creditEntities;
    }

    public void setCredits(Collection<CreditEntity> creditEntities) {
        this.creditEntities = creditEntities;
    }
    //endregion
}
