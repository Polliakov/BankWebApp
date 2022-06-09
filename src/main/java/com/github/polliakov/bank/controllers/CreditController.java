package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.CreditDto;
import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.BankEntity;
import com.github.polliakov.bank.services.CreditService;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditController {
    public CreditController(CreditService service, DtoMapperService dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    private final CreditService service;
    private final DtoMapperService dtoMapper;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreditDto creditDto) {
        try {
            var credit = dtoMapper.entityFromDto(creditDto);
            service.create(credit);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/banks")
    public ResponseEntity<List<BankEntity>> getBanks(@PathVariable Long id){
        try {
            var banks = service.getBanks(id);
            var bankDtos = banks.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(banks, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/credit offers")
    public ResponseEntity<List<CreditOfferDto>> getCreditOffers(@PathVariable Long id){
        try {
            var banks = service.getCreditOffers(id);
            var bankDtos = banks.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(bankDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<CreditDto>> getAll() {
        try {
            var credits = service.getAll();
            var creditDtos = credits.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(creditDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditDto>  getById(@PathVariable Long id) {
        var credit = service.getById(id);
        return credit != null ?
                new ResponseEntity<>(dtoMapper.dtoFromEntity(credit), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CreditDto creditDto) {
        try {
            var credit = dtoMapper.entityFromDto(creditDto);
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
