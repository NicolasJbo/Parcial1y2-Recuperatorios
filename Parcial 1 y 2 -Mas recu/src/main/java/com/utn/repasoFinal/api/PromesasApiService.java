package com.utn.repasoFinal.api;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.utn.repasoFinal.model.Jugador;
import com.utn.repasoFinal.model.JugadorPromesa;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PromesasApiService {

    @CircuitBreaker(name="secondChoice", fallbackMethod = "fallback")
    public List<JugadorPromesa> getJugadoresPromesas() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://app.sportdataapi.com/api/v1/soccer/players?apikey=0c0149f0-e126-11eb-93b0-f7581c662435&country_id=13"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());


        List<JugadorPromesa> jugadoresList = Arrays.asList(new Gson().fromJson(JsonParser.parseString(response.body()).getAsJsonObject().get("data"),JugadorPromesa[].class));

        List<JugadorPromesa> promesas = new ArrayList<>();
        Integer height, age;
        for(JugadorPromesa j : jugadoresList){
           height = j.getHeight(); age = j.getAge();
            if( (height != null && age != null) && (height>180 && age <=20) )
                promesas.add(j);
        }

        return promesas;
    }

    public List<JugadorPromesa> fallback(final Throwable t){
        return Collections.emptyList();
    }
}
