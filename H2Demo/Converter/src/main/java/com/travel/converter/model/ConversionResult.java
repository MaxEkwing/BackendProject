package com.travel.converter.model;

public class ConversionResult {
    private double originalAmount;
    private double convertedAmount;
    private String fromCurrency;
    private String toCurrency;

    public ConversionResult() {
    }

    public ConversionResult(double originalAmount, double convertedAmount, String fromCurrency, String toCurrency) {
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Override
    public String toString() {
        return "ConversionResult{" +
                "originalAmount=" + originalAmount +
                ", convertedAmount=" + convertedAmount +
                ", fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                '}';
    }
}
