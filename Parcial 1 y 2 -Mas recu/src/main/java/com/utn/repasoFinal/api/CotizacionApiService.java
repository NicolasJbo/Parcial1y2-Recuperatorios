package com.utn.repasoFinal.api;

import com.google.gson.JsonParser;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class CotizacionApiService {

    @CircuitBreaker(name="secondChoice", fallbackMethod = "fallback")
    public Double getCotizacionEuro() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.dolarsi.com/api/api.php?type=genedolar&opc=ta"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String cotizacion = JsonParser.parseString(response.body()).getAsJsonArray().get(0).getAsJsonObject().get("dolar").getAsJsonObject().get("compra").getAsString();

        return Double.parseDouble(cotizacion.replace(",",".") );
    }

    @CircuitBreaker(name="secondChoice", fallbackMethod = "fallback")
    public Double getCotizacionDolar() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.dolarsi.com/api/api.php?type=dolar"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String cotizacion = JsonParser.parseString(response.body()).getAsJsonArray().get(0).getAsJsonObject().get("casa").getAsJsonObject().get("compra").getAsString();

        return Double.parseDouble(cotizacion.replace(",",".") );
    }

    public Double fallback(final Throwable t){
        return Double.valueOf(100D);
    }
}
