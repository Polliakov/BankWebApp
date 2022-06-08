package com.github.polliakov.bank.dto;

import java.math.BigDecimal;
import java.util.List;

public class CreditDto {
    public Long id;
    public BigDecimal sumLimit;
    public double rate;
    public List<Long> bankEntitiesIds;
}
