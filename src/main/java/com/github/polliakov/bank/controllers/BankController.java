package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.BankDto;
import com.github.polliakov.bank.dto.ClientDto;
import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.services.BankService;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/banks")
public class BankController {
    public BankController(BankService service, DtoMapperService dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    private final BankService service;
    private final DtoMapperService dtoMapper;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BankDto bankDto) {
        try {
            var bank = dtoMapper.entityFromDto(bankDto);
            service.create(bank);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/clients")
    public ResponseEntity<List<ClientDto>> getClients(@PathVariable Long id) {
        try {
            var clients = service.getClients(id);
            var clientDtos = clients.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(clientDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credits")
    public ResponseEntity<List<CreditDto>> getCredits(@PathVariable Long id) {
        try {
            var credits = service.getCredits(id);
            var creditDtos = credits.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(creditDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<BankDto>> getAll() {
        try {
            var banks = service.getAll();
            var bankDtos = banks.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(bankDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankDto> getById(@PathVariable Long id) {
        var bank = service.getById(id);
        return bank != null ?
                new ResponseEntity<>(dtoMapper.dtoFromEntity(bank), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BankDto bankDto) {
        try {
            var bank = dtoMapper.entityFromDto(bankDto);
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
