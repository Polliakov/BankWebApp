package com.github.polliakov.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private String patronymic;
    @NotNull
    @Size(min = 11, max = 11)
    private String phoneNumber;
    @NotNull
    @Email(regexp = "^\\d{11}$")
    private String email;
    @NotNull
    @Size(min = 10, max = 10)
    private String passportNumber;
    private List<Long> bankEntitiesIds;
}
