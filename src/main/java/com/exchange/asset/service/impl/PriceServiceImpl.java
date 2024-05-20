package com.exchange.asset.service.impl;

import com.exchange.asset.config.ErrorCode;
import com.exchange.asset.exception.AppException;
import com.exchange.asset.service.MessageTranslationService;
import com.exchange.asset.service.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final MessageTranslationService messageTranslationService;
    private final Random random = new Random();
    private Map<String, Double> prices = new HashMap<>();

    @Override
    public void setPrice(String symbol, double price) {
        log.info("Setting price for: symbol={}, price={}", symbol, price);
        prices.put(symbol, price);
    }

    @Override
    public double getPrice(String symbol) {
        log.info("Fetching price for: symbol={}", symbol);
        double price = __getPrice(symbol);
        log.info("Fetched price for: symbol={}, price={}", symbol, price);
        return price;
    }

    private double __getPrice(String symbol) {
        if (!prices.containsKey(symbol)) {
            ErrorCode errorCode = ErrorCode.PRICE_NOT_FOUND;
            String userMsg = messageTranslationService.getMessage(errorCode.getErrorCode(), new Object[]{symbol});
            throw new AppException(errorCode, userMsg);
        }
        return prices.get(symbol);
    }

    @Override
    public double getPrice2(String symbol) {
        log.info("Fetching price for: symbol={}", symbol);
        throwRandomException();
        double price = __getPrice(symbol);
        log.info("Fetched price for: symbol={}, price={}", symbol, price);
        return price;

    }

    private void throwRandomException() {
        if (random.nextBoolean()) {
            throw new RuntimeException("server_error");
        }
    }
}