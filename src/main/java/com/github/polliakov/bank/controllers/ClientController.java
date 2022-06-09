package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.dto.ClientDto;
import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.services.ClientService;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    public ClientController(ClientService service, DtoMapperService dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    private final ClientService service;
    private final DtoMapperService dtoMapper;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientDto clientDto) {
        try {
            var client = dtoMapper.entityFromDto(clientDto);
            service.create(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/banks")
    public ResponseEntity<List<BankDto>> getBanks(@PathVariable Long id) {
        try {
            var banks = service.getBanks(id);
            var bankDtos = banks.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(bankDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credit offers")
    public ResponseEntity<List<CreditOfferDto>> getCreditOffers(@PathVariable Long id) {
        try {
            var creditOffers = service.getCreditOffers(id);
            var creditOfferDtos = creditOffers.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(creditOfferDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        try {
            var clients = service.getAll();
            var clientDtos = clients.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(clientDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable Long id) {
        var client = service.getById(id);
        return client != null ?
                new ResponseEntity<>(dtoMapper.dtoFromEntity(client), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ClientDto clientDto) {
        try {
            var client = dtoMapper.entityFromDto(clientDto);
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
