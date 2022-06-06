package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.CreditEntity;
import com.github.polliakov.bank.services.CreditService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CreditController {

    private final CreditService service;

    public CreditController(CreditService service) { this.service = service;}

    @GetMapping("/credit banks/{id}")
    public ResponseEntity<List<BankEntity>> getBanks(@PathVariable Long id){
        try {
            var banks = service.getBanks(id);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/credits")
    public ResponseEntity<List<CreditEntity>> getAll() {
        try {
            var credits = service.getAll();
            return new ResponseEntity<>(credits, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<CreditEntity>  getById(@PathVariable Long id) {
        var credit = service.getById(id);
        return credit != null ?
                new ResponseEntity<>(credit, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add credit")
    public ResponseEntity<?> create(@RequestBody CreditEntity credit) {
        try {
            service.create(credit);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update credit")
    public ResponseEntity<?> update(@RequestBody CreditEntity credit) {
        try {
            service.update(credit);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete credit/{id}")
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
