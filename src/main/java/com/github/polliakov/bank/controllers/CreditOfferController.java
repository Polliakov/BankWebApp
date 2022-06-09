package com.github.polliakov.bank.controllers;

import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.dto.CreditPaymentDto;
import com.github.polliakov.bank.services.CreditOfferService;
import com.github.polliakov.bank.services.DtoMapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("credit offers")
public class CreditOfferController {
    public CreditOfferController(CreditOfferService service, DtoMapperService dtoMapper) {
        this.service = service;
        this.dtoMapper = dtoMapper;
    }

    private final CreditOfferService service;
    private final DtoMapperService dtoMapper;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreditOfferDto creditOfferDto) {
        try {
            var creditOffer = dtoMapper.entityFromDto(creditOfferDto);
            service.create(creditOffer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<CreditOfferDto>> getAll(){
        var creditOffers = service.getAll();
        var creditOfferDtos = creditOffers.stream().map(dtoMapper::dtoFromEntity).toList();
        return new ResponseEntity<>(creditOfferDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditOfferDto> getById(@PathVariable Long id) {
        var creditOffer = service.getById(id);
        return creditOffer != null ?
                new ResponseEntity<>(dtoMapper.dtoFromEntity(creditOffer), HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/payments")
    public ResponseEntity<List<CreditPaymentDto>> getPayments(@PathVariable Long id) {
        try {
            var payments = service.getPayments(id);
            var paymentDtos = payments.stream().map(dtoMapper::dtoFromEntity).toList();
            return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody CreditOfferDto creditOfferDto) {
        try {
            var creditOffer = dtoMapper.entityFromDto(creditOfferDto);
            service.update(creditOffer);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
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
