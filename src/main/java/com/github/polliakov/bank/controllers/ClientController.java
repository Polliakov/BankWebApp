package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.entities.ClientEntity;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    public ClientController(ClientService service) { this.service = service; }

    private final ClientService service;

    @GetMapping("/{id}/banks")
    public ResponseEntity<List<BankEntity>> getBanks(@PathVariable Long id) {
        try {
            var banks = service.getBanks(id);
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credit offers")
    public ResponseEntity<List<CreditOfferEntity>> getCreditOffers(@PathVariable Long id) {
        try {
            var creditOffers = service.getCreditOffers(id);
            return new ResponseEntity<>(creditOffers, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientEntity>> getAll() {
        try {
            var clients = service.getAll();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getById(@PathVariable Long id) {
        var client = service.getById(id);
        return client != null ?
                new ResponseEntity<>(client, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ClientEntity client) {
        try {
            service.create(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClientEntity client) {
        try {
            service.update(client);
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
