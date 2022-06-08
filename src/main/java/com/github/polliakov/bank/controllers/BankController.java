package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.services.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {
    public BankController(BankService service) {
        this.service = service;
    }

    private final BankService service;

    @GetMapping("/{id}/clients")
    public ResponseEntity<List<ClientEntity>> getClients(@PathVariable Long id) {
        try {
            var clients = service.getClients(id);
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credits")
    public ResponseEntity<List<CreditEntity>> getCredits(@PathVariable Long id) {
        try {
            var credits = service.getCredits(id);
            return new ResponseEntity<>(credits, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BankEntity>> getAll() {
        try {
            var banks = service.getAll();
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankEntity> getById(@PathVariable Long id) {
        var bank = service.getById(id);
        return bank != null ?
                new ResponseEntity<>(bank, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BankDto bankDto) {
        try {
            service.create(bankDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BankEntity bank) {
        try {
            service.update(bank);
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
