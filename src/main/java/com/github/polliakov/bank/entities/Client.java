package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "clients")
public class Client {
    public Client() { }

    public Client(Long id,
                  String name, String surname, String patronymic,
                  String phoneNumber, String email, String passportNumber,
                  Collection<CreditOffer> creditOffers,
                  Collection<Bank> banks) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
        this.creditOffers = creditOffers;
        this.banks = banks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private String patronymic;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String passportNumber;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Collection<CreditOffer> creditOffers;

    @ManyToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private Collection<Bank> banks;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Collection<CreditOffer> getCreditOffers() {
        return creditOffers;
    }

    public void setCreditOffers(Collection<CreditOffer> creditOffers) {
        this.creditOffers = creditOffers;
    }

    public Collection<Bank> getBanks() {
        return banks;
    }

    public void setBanks(Collection<Bank> banks) {
        this.banks = banks;
    }
    //endregion
}
