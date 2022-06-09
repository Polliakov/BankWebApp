package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.repositories.CreditOfferRepository;
import com.github.polliakov.bank.repositories.CreditPaymentRepository;
import com.github.polliakov.bank.services.CreditOfferService;
import com.github.polliakov.bank.services.CreditPaymentService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {
    public CreditOfferServiceImpl(CreditOfferRepository creditOfferRepository,
                                  CreditPaymentRepository paymentRepository,
                                  CreditPaymentService paymentService) {
        this.creditOfferRepository = creditOfferRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    private final CreditOfferRepository creditOfferRepository;
    private final CreditPaymentRepository paymentRepository;
    private final CreditPaymentService paymentService;

    @Override
    public void create(CreditOfferEntity creditOffer) {
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
    public void update(CreditOfferEntity newCreditOffer) {
        var oldCreditOffer = creditOfferRepository.findById(newCreditOffer.getId()).orElseThrow();
        List<Long> oldPaymentsIds = oldCreditOffer.getPayments().stream().map(p -> p.getId()).toList();
        oldCreditOffer.getPayments().clear();
        paymentRepository.deleteAllById(oldPaymentsIds);

        addPayments(newCreditOffer);
        creditOfferRepository.save(newCreditOffer);
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
}
