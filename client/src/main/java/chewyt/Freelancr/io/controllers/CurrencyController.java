package chewyt.Freelancr.io.controllers;

import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chewyt.Freelancr.io.services.CurrencyService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/currency", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;
    
    private final Logger logger = Logger.getLogger(CurrencyController.class.getName());

    @PostMapping(consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> postRegister(@RequestBody MultiValueMap<String, String> form){

        Double project_cost = Double.parseDouble(form.getFirst("project_cost"));
        logger.info("Project Cost from POST >>>>> %.2f".formatted(project_cost));


        //run service to return cost

        Double converted_amt;

        try {
            converted_amt = currencyService.convertETH(project_cost);
        } catch (Exception ex) {
            JsonObject err = Json.createObjectBuilder()
            .add("message", ex.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        JsonObject object = Json.createObjectBuilder()
            .add("amount",converted_amt)
            .build();

        return ResponseEntity.ok(object.toString());

    }

}
