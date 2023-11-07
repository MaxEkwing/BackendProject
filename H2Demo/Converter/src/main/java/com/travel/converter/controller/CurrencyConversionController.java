package com.travel.converter.controller;

import com.travel.converter.model.ConversionResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    private double sekToPlnRate = 0.382;

    @GetMapping("/convert/sek-to-pln")
    public ConversionResult sekToPln(@RequestParam double amount) {
        double convertedAmount = amount * sekToPlnRate;
        return new ConversionResult(amount, convertedAmount, "SEK", "PLN");
    }


}
