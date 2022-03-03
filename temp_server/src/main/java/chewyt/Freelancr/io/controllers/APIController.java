package chewyt.Freelancr.io.controllers;


import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chewyt.Freelancr.io.models.User;
import chewyt.Freelancr.io.services.UserService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

    @Autowired
    private UserService userService;
    
    private final Logger logger = Logger.getLogger(APIController.class.getName());
    
    private Optional<Integer> opt;
    private Optional<User> optUser;
    
    @PostMapping(path="/register",consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRegisterJson(@RequestBody String payload){

        logger.info("payload>>>>>>>>>> "+payload);
        User user = null;
        JsonObject err;
        
        try {
            user = User.create(payload);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            err = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        try {
            
            opt = userService.addUser(user);
        } catch (Exception e) {
            err =Json.createObjectBuilder()
                .add("message","Failed to create new User due to SQL Exceptions")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        if (opt.isEmpty())
        {
            logger.info("Created success message for new account...sending response back...");
            return ResponseEntity.status(HttpStatus.CREATED).body("{}");
        }
        switch (opt.get()) {
            case 401:
                err =Json.createObjectBuilder()
                    .add("message","Email addresss already exists".formatted(user.getUsername()))
                    .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err.toString());
                
        
            default:
                err =Json.createObjectBuilder()
                .add("message","Failed to create new User")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
    }

    @PostMapping(path="/login",consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authUser(@RequestBody String payload){

        logger.info("payload>>>>>>>>>> "+payload);
        User user = null;
        JsonObject err;
        
        try {
            logger.info("Extracting creds");
            user=User.extractCredentials(payload);
        } catch (Exception e) {
            err =Json.createObjectBuilder()
            .add("message","Failed to create new User due to SQL Exceptions")
            .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
        
        logger.info("Using service....");
        optUser = userService.authUser(user.getEmail(),user.getPassword());
        
        if (optUser.isEmpty())
        {
            err = Json.createObjectBuilder()
            .add("message","User authetication failed")
            .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err.toString());

        }else{

            return ResponseEntity.ok(optUser.get().toJSON().toString());
        }
        
    }
    

    
}
