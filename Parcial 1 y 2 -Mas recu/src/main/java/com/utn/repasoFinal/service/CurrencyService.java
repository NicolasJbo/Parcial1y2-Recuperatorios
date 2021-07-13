package com.utn.repasoFinal.service;

import com.utn.repasoFinal.model.Currency;
import com.utn.repasoFinal.model.Persona;
import com.utn.repasoFinal.model.dto.CurrencyDto;
import com.utn.repasoFinal.model.dto.PersonaDto;
import com.utn.repasoFinal.repository.CurrencyRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public Currency addCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency getCurrencyById(Integer idCurrency) throws NotFoundException {
        return currencyRepository.findById(idCurrency)
                .orElseThrow(()-> new NotFoundException("No se encontro el registo "+idCurrency+" en la tabla CURRENCY"));
    }

    public Page<CurrencyDto> getAllCurrencys(Integer page, Integer size, List<Sort.Order> orders) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Currency>currencys = currencyRepository.findAll(pageable);

        Page<CurrencyDto> currencysDto =  Page.empty();

        if(!currencys.isEmpty())
            currencysDto = currencys.map(c -> CurrencyDto.from(c));

        return currencysDto;
    }

    public void deleteCurrencyByID(Integer idCurrency) throws NotFoundException {
        if(!currencyRepository.existsById(idCurrency))
            throw new NotFoundException("No se encontro el registo "+idCurrency+" en la tabla CURRENCY");

        currencyRepository.deleteById(idCurrency);
    }
}
