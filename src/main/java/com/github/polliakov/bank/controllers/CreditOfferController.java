package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.services.CreditOfferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditOfferController {
    public CreditOfferController(CreditOfferService service) {
        this.service = service;
    }

    private final CreditOfferService service;

    @PostMapping("/add credit offer")
    public void create(@RequestBody CreditOfferEntity creditOffer) {
        service.create(creditOffer);
    }

    @GetMapping("/credit offers")
    public List<CreditOfferEntity> getAll(){
        return service.getAll();
    }

    @GetMapping("/credit offer/{id}")
    public CreditOfferEntity getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/credit offer payments/{id}")
    public List<CreditPaymentEntity> getPayments(@PathVariable Long id){
        return service.getPayments(id);
    }

    @DeleteMapping("delete credit offer/{id}")
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
