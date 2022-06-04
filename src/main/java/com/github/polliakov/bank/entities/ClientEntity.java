package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "clients")
public class ClientEntity {
    public ClientEntity() { }

    public ClientEntity(Long id,
                        String name, String surname, String patronymic,
                        String phoneNumber, String email, String passportNumber,
                        Collection<CreditOfferEntity> creditOfferEntities,
                        Collection<BankEntity> bankEntities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passportNumber = passportNumber;
        this.creditOfferEntities = creditOfferEntities;
        this.bankEntities = bankEntities;
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

    @OneToMany(mappedBy = "clientEntity", fetch = FetchType.EAGER)
    private Collection<CreditOfferEntity> creditOfferEntities;

    @ManyToMany(mappedBy = "clientEntities", fetch = FetchType.LAZY)
    private Collection<BankEntity> bankEntities;

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

    public Collection<CreditOfferEntity> getCreditOffers() {
        return creditOfferEntities;
    }

    public void setCreditOffers(Collection<CreditOfferEntity> creditOfferEntities) {
        this.creditOfferEntities = creditOfferEntities;
    }

    public Collection<BankEntity> getBanks() {
        return bankEntities;
    }

    public void setBanks(Collection<BankEntity> bankEntities) {
        this.bankEntities = bankEntities;
    }
    //endregion
}
