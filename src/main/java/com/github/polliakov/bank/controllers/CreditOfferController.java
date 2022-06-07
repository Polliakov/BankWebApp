package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.services.CreditOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditOfferController {
    public CreditOfferController(CreditOfferService service) {
        this.service = service;
    }

    private final CreditOfferService service;

    @PostMapping("/add credit offer")
    public ResponseEntity<?> create(@RequestBody CreditOfferDto creditOffer) {
        try {
            service.create(creditOffer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/credit offers")
    public ResponseEntity<List<CreditOfferEntity>> getAll(){
        var creditOffers = service.getAll();
        return new ResponseEntity<>(creditOffers, HttpStatus.OK);
    }

    @GetMapping("/credit offer/{id}")
    public ResponseEntity<CreditOfferEntity> getById(@PathVariable Long id) {
        var creditOffer = service.getById(id);
        return creditOffer != null ?
                new ResponseEntity<>(creditOffer, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/credit offer payments/{id}")
    public ResponseEntity<List<CreditPaymentEntity>> getPayments(@PathVariable Long id) {
        try {
            var payments = service.getPayments(id);
            return new ResponseEntity<>(payments, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update credit offer")
    public ResponseEntity<?> update(@RequestBody CreditOfferDto creditOfferDto) {
        try {
            service.update(creditOfferDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete credit offer/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }
}
