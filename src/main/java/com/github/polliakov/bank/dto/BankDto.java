package com.github.polliakov.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
    private Long id;
    private List<Long> clientEntitiesIds;
    private List<Long> creditEntitiesIds;
}
