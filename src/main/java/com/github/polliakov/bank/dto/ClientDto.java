package com.github.polliakov.bank.dto;

import java.util.List;

public class ClientDto {
    public Long id;
    public String name;
    public String surname;
    public String patronymic;
    public String phoneNumber;
    public String email;
    public String passportNumber;
    public List<Long> bankEntitiesIds;
}
