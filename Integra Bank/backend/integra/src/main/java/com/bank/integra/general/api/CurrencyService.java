package com.bank.integra.general.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {
    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CurrencyService(RestTemplate restTemplate, @Value("${app.api-currency-url}")String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }



    public Map<String, Map<String, String>> getUsdExchangeRate() {
        List<Map<String, String>> exchangeRates = Arrays.asList(restTemplate.getForObject(apiUrl, Map[].class));

        return exchangeRates.stream()
                .filter(rate -> "USD".equals(rate.get("ccy")))
                .collect(Collectors.toMap(
                        rate -> rate.get("ccy"),
                        rate -> Map.of("buy", rate.get("buy"), "sale", rate.get("sale"))
                ));
    }
}
