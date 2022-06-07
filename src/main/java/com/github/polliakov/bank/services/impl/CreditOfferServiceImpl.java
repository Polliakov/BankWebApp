package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.dto.CreditOfferDto;
import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.repositories.ClientRepository;
import com.github.polliakov.bank.repositories.CreditOfferRepository;
import com.github.polliakov.bank.repositories.CreditPaymentRepository;
import com.github.polliakov.bank.repositories.CreditRepository;
import com.github.polliakov.bank.services.CreditOfferService;
import com.github.polliakov.bank.services.CreditPaymentService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {
    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository,
                                  CreditPaymentService paymentService,
                                  CreditRepository creditRepository,
                                  ClientRepository clientRepository,
                                  CreditPaymentRepository paymentRepository) {
        this.creditOfferRepository = creditOfferRepository;
        this.clientRepository = clientRepository;
        this.creditRepository = creditRepository;
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }

    private final CreditOfferRepository creditOfferRepository;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final CreditPaymentRepository paymentRepository;
    private final CreditPaymentService paymentService;

    @Override
    public void create(CreditOfferDto creditOfferDto) {
        var creditOffer = creditOfferEntityFromDto(creditOfferDto);
        creditOffer.setId(null);
        addPayments(creditOffer);
        creditOfferRepository.save(creditOffer);
        paymentRepository.saveAll(creditOffer.getPayments());
    }

    @Override
    public CreditOfferEntity getById(Long id) {
        return creditOfferRepository.findById(id).orElse(null);
    }

    @Override
    public List<CreditOfferEntity> getAll() {
        return creditOfferRepository.findAll();
    }

    @Override
    public List<CreditPaymentEntity> getPayments(Long creditOfferId) {
        var credit = getById(creditOfferId);
        if (credit == null)
            throw new FindException("Credit with id = " + creditOfferId + "is not found");
        return new LinkedList<>(credit.getPayments());
    }

    @Override
    public void update(CreditOfferDto creditOfferDto) {
        var creditOffer = creditOfferEntityFromDto(creditOfferDto);
        CheckExists(creditOffer.getId());
        addPayments(creditOffer);
        creditOfferRepository.save(creditOffer);
        paymentRepository.saveAll(creditOffer.getPayments());
    }

    @Override
    public void deleteById(Long id) {
        CheckExists(id);
        creditOfferRepository.deleteById(id);
    }

    private void addPayments(CreditOfferEntity creditOffer) {
        var creditPayments = paymentService.create(creditOffer, 12);
        creditOffer.setPayments(creditPayments);
    }

    private void CheckExists(Long id) {
        if (!creditOfferRepository.existsById(id))
            throw new FindException("Bank with id = " + id + "is not found");
    }

    private CreditOfferEntity creditOfferEntityFromDto(CreditOfferDto creditOfferDto) {
        if (creditOfferDto == null)
            throw new NullPointerException("creditOffer");

        var credit = creditRepository.findById(creditOfferDto.creditId).orElseThrow();
        var client = clientRepository.findById(creditOfferDto.clientId).orElseThrow();

        var creditOffer = new CreditOfferEntity();
        creditOffer.setId(creditOfferDto.id);
        creditOffer.setTotal(creditOfferDto.total);
        creditOffer.setCredit(credit);
        creditOffer.setClient(client);

        if (creditOffer.getTotal().compareTo(credit.getSumLimit()) > 0)
            throw new IllegalArgumentException("creditOffer.total greater than credit limit.");

        return creditOffer;
    }
}
