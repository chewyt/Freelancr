package chewyt.Freelancr.io.controllers;


import java.util.LinkedList;
import java.util.List;
// import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chewyt.Freelancr.io.models.Finterest;
import chewyt.Freelancr.io.repositories.FinterestRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/finterest", produces = MediaType.APPLICATION_JSON_VALUE)
public class FinterestController {

    @Autowired
    FinterestRepository finterestRepository;
    
    private final Logger logger = Logger.getLogger(FinterestController.class.getName());
    
    private boolean opt =false;
    // private Optional<User> optUser;
    
    @PostMapping(path="/new",consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRegisterJson(@RequestBody String payload){

        logger.info("payload>>>>>>>>>> "+payload);
        Finterest finterest = null;
        JsonObject err;
        
        try {
            finterest = Finterest.create(payload);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            err = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        try {
            if(!finterestRepository.hasUser(finterest)){

                opt = finterestRepository.addFinterest(finterest);
            }

            else{
                err =Json.createObjectBuilder()
                .add("message","User already indicated interest for this project")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
            }
        } catch (Exception e) {
            err =Json.createObjectBuilder()
                .add("message","Failed to create new User due to SQL Exceptions")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        if (opt)
        {
            logger.info("Created success message for new finterest...sending response back...");
            return ResponseEntity.status(HttpStatus.CREATED).body("{}");
        }
        else {

            err =Json.createObjectBuilder()
                .add("message","Failed to create new User")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
    }

   
    @GetMapping(path="/{project_id}")
    public ResponseEntity<String> getUserCreds(@PathVariable String project_id){
        
        logger.info("Project Id for pulling Finterest : "+project_id);
        
        List<Finterest> finterests = new LinkedList<>();
        JsonObject err;

        try {
            finterests = finterestRepository.getFinterests(project_id);
        } catch (Exception ex) {
            err = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }


        List<JsonObject> j_finterests = finterests.stream()
				.map(p -> p.toJSON())
				.toList();
        
        return ResponseEntity.ok(Json.createArrayBuilder(j_finterests).build().toString());
    }

    

    
}
