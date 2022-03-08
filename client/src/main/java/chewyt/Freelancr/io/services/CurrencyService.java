package chewyt.Freelancr.io.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static chewyt.Freelancr.io.Constants.*;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class CurrencyService {
    Logger logger = Logger.getLogger(CurrencyService.class.getName());
    
    public Double convertETH(Double project_cost){

        logger.info(ENV_APIKEY_PRIVATE.toString());

        String url = UriComponentsBuilder
                .fromUriString(URL_API)
                .queryParam("CMC_PRO_API_KEY", ENV_APIKEY_PRIVATE)
                .queryParam("start", "2")
                .queryParam("limit", "1")
                .queryParam("convert_id", "2808")  //SGD SYMBOL ID 
                .toUriString();

        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        List<Double> prices = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject result = reader.readObject();
            final JsonArray searchresults = result.getJsonArray("data");
            prices = searchresults.stream()
                .map(r->(JsonObject)r)
                .map(r->r.getJsonObject("quote"))
                .map(r->r.getJsonObject("2808"))
                .map(quote-> quote.getJsonNumber("price").doubleValue())
                // .map(price->Double.parseDouble(price))
                .collect(Collectors.toList());

        } catch (Exception e) {
            logger.severe("GG: %s".formatted(e.getMessage()));
        }

        logger.info("Project Cost:"+project_cost);
        logger.info("ETH rate:"+prices.get(0));
        logger.info("Converted amount:"+project_cost/prices.get(0));
        return project_cost/ prices.get(0);

    }
}
