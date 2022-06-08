package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.services.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditController {
    public CreditController(CreditService service) { this.service = service;}

    private final CreditService service;

    @GetMapping("/{id}/banks")
    public ResponseEntity<List<BankEntity>> getBanks(@PathVariable Long id){
        try {
            var banks = service.getBanks(id);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credit offers")
    public ResponseEntity<List<CreditOfferEntity>> getCreditOffers(@PathVariable Long id){
        try {
            var banks = service.getCreditOffers(id);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CreditEntity>> getAll() {
        try {
            var credits = service.getAll();
            return new ResponseEntity<>(credits, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditEntity>  getById(@PathVariable Long id) {
        var credit = service.getById(id);
        return credit != null ?
                new ResponseEntity<>(credit, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreditDto creditDto) {
        try {
            service.create(creditDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CreditEntity credit) {
        try {
            service.update(credit);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
