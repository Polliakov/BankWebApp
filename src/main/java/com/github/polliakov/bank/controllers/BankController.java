package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.services.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    private final BankService service;

    public BankController(BankService service) {
        this.service = service;
    }

    @GetMapping("/bank clients/{id}")
    public ResponseEntity<List<ClientEntity>> getClients(@PathVariable Long id) {
        try {
            var clients = service.getClients(id);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bank credits/{id}")
    public ResponseEntity<List<CreditEntity>> getCredits(@PathVariable Long id) {
        try {
            var credits = service.getCredits(id);
            return new ResponseEntity<>(credits, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/banks")
    public ResponseEntity<List<BankEntity>> getAll() {
        try {
            var banks = service.getAll();
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bank/{id}")
    public ResponseEntity<BankEntity>  getById(@PathVariable Long id) {
        var bank = service.getById(id);
        return bank != null ?
                new ResponseEntity<>(bank, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add bank")
    public ResponseEntity<?> create(@RequestBody BankEntity bank) {
        try {
            service.create(bank);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update bank")
    public ResponseEntity<?> update(@RequestBody BankEntity bank) {
        try {
            service.update(bank);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete bank/{id}")
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
