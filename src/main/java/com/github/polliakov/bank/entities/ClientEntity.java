package com.github.polliakov.bank.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.regex.Pattern;

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

    @Column(nullable = false, unique = true, length = 11)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true, length = 10)
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
        if (name.isBlank())
            throw new IllegalArgumentException();
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (surname.isBlank())
            throw new IllegalArgumentException();
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic.isBlank() ? null : patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.isBlank())
            throw new IllegalArgumentException();
        if (!Pattern.compile("\\d{11}").matcher(phoneNumber).matches())
            throw new IllegalArgumentException();
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.isBlank())
            throw new IllegalArgumentException();
        if (!Pattern.compile("^(.+)@(\\S+)$").matcher(email).matches())
            throw new IllegalArgumentException();
        this.email = email;
    }

    public String getPassportNumber() {
        if (!Pattern.compile("\\d{10}").matcher(phoneNumber).matches())
            throw new IllegalArgumentException();
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
