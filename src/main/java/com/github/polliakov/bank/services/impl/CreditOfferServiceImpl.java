package com.github.polliakov.bank.services.impl;

import com.github.polliakov.bank.entities.CreditOfferEntity;
import com.github.polliakov.bank.entities.CreditPaymentEntity;
import com.github.polliakov.bank.repositories.CreditOfferRepository;
import com.github.polliakov.bank.services.CreditOfferService;
import com.github.polliakov.bank.services.CreditPaymentService;
import org.springframework.stereotype.Service;

import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditOfferServiceImpl implements CreditOfferService {
    public CreditOfferServiceImpl(CreditOfferRepository repository, CreditPaymentService paymentService) {
        this.repository = repository;
        this.paymentService = paymentService;
    }

    private final CreditOfferRepository repository;
    private final CreditPaymentService paymentService;

    @Override
    public void create(CreditOfferEntity creditOffer) {
        // region Validation
        if (creditOffer == null)
            throw new NullPointerException("creditOffer");
        if (creditOffer.getClient() == null)
            throw new NullPointerException("creditOffer.client");
        var credit = creditOffer.getCredit();
        if (credit == null)
            throw new NullPointerException("creditOffer.credit");
        if (creditOffer.getTotal().compareTo(credit.getSumLimit()) > 0)
            throw new IllegalArgumentException("creditOffer.total greater than credit limit.");
        // endregion

        var creditPayments = paymentService.calculatePayments(creditOffer, 12);
        creditOffer.setPayments(creditPayments);
        repository.save(creditOffer);
    }

    @Override
    public CreditOfferEntity getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<CreditOfferEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public List<CreditPaymentEntity> getPayments(Long creditOfferId) {
        var credit = getById(creditOfferId);
        if (credit == null)
            throw new FindException("Credit with id = " + creditOfferId + "is not found");
        return new ArrayList<>(credit.getPayments());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private void CheckExists(Long id) {
        if (!repository.existsById(id))
            throw new FindException("Bank with id = " + id + "is not found");
    }
}
