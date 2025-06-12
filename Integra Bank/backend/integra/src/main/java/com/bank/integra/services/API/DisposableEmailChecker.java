package com.bank.integra.services.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class DisposableEmailChecker {
    @Autowired
    private RestTemplate restTemplate;
    private final String API_URL = "https://disposable.debounce.io/";

    public DisposableEmailChecker() {}

    public boolean isEmailDisposable(String email) {
        String url = UriComponentsBuilder.fromHttpUrl(API_URL)
                .queryParam("email", email)
                .toUriString();

        try {
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            return Boolean.parseBoolean(response.get("disposable"));
        } catch(Exception e) {
            System.out.println("Ошибка запроса: " + e.getMessage());
            return true;
        }
    }

}
