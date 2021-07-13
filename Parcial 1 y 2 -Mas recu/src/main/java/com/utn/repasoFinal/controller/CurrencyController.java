package com.utn.repasoFinal.controller;

import com.utn.repasoFinal.api.CotizacionApiService;
import com.utn.repasoFinal.model.Currency;
import com.utn.repasoFinal.model.Persona;
import com.utn.repasoFinal.model.dto.CurrencyDto;
import com.utn.repasoFinal.model.dto.PersonaDto;
import com.utn.repasoFinal.service.CurrencyService;
import javassist.NotFoundException;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private CurrencyService currencyService;
    private CotizacionApiService cotizacionApiService;

    @Autowired
    public CurrencyController(CurrencyService currencyService, CotizacionApiService cotizacionApiService) {
        this.currencyService = currencyService;
        this.cotizacionApiService = cotizacionApiService;
    }

    @PostMapping
    public ResponseEntity addCurrency(@RequestBody Currency currency){
        Currency c = currencyService.addCurrency(currency);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idCurrency}")
                .buildAndExpand(c.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idCurrency}")
    public ResponseEntity getCurrencyById(@PathVariable Integer idCurrency) throws NotFoundException {
        Currency c = currencyService.getCurrencyById(idCurrency);
        return ResponseEntity.ok(c);
    }

    @GetMapping
    public ResponseEntity getAllCurrencys(@RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "5") Integer size,
                                         @RequestParam(defaultValue = "id") String sortField1,
                                         @RequestParam(defaultValue = "monto") String sortField2){
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.ASC, sortField1));
        orders.add(new Sort.Order(Sort.Direction.ASC, sortField2));

        Page<CurrencyDto> currencys = currencyService.getAllCurrencys(page, size, orders);

        if(currencys.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-Total-Elements", Long.toString(currencys.getTotalElements()))
                    .header("X-Total-Pages", Long.toString(currencys.getTotalPages()))
                    .header("X-Actual-Page", Integer.toString(page))
                    .body(currencys.getContent());
    }

    @DeleteMapping("/{idCurrency}")
    public ResponseEntity deleteCurrencyByID(@PathVariable Integer idCurrency) throws NotFoundException {
        currencyService.deleteCurrencyByID(idCurrency);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/euro")
    public ResponseEntity getEuroCotizacion() throws IOException, InterruptedException {
        Double cotizacion = cotizacionApiService.getCotizacionEuro();
        return ResponseEntity.ok(cotizacion);
    }

    @GetMapping("/dolar")
    public ResponseEntity getDolarCotizacion() throws IOException, InterruptedException {
        Double cotizacion = cotizacionApiService.getCotizacionDolar();
        return ResponseEntity.ok(cotizacion);
    }


}
