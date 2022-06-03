package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "banks")
public class Bank {
    public Bank () { }

    public Bank(Long id, Collection<Client> clients, Collection<Credit> credits) {
        this.id = id;
        this.clients = clients;
        this.credits = credits;
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
    private Collection<Client> clients;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "credits_in_banks",
            joinColumns = @JoinColumn(name = "bank_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "credit_id", nullable = false)
    )
    private Collection<Credit> credits;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Client> getClients() {
        return clients;
    }

    public void setClients(Collection<Client> clients) {
        this.clients = clients;
    }

    public Collection<Credit> getCredits() {
        return credits;
    }

    public void setCredits(Collection<Credit> credits) {
        this.credits = credits;
    }
    //endregion
}
