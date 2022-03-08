package chewyt.Freelancr.io.controllers;


import java.util.Optional;
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

import chewyt.Freelancr.io.models.Project;
import chewyt.Freelancr.io.repositories.ProjectRepository;
import chewyt.Freelancr.io.services.ProjectService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectRepository projectRepository;

    
    
    private final Logger logger = Logger.getLogger(ProjectController.class.getName());
    
    private Optional<Integer> opt;
    // private Optional<Project> optProject;
    
    @PostMapping(path="/new",consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRegisterJson(@RequestBody String payload){

        logger.info("payload>>>>>>>>>> "+payload);
        Project project = null;
        JsonObject err;
        
        try {
            project = Project.create(payload);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());
            err = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        try {
            
            opt = projectService.addProject(project);
        } catch (Exception e) {
            err =Json.createObjectBuilder()
                .add("message","Failed to create new User due to SQL Exceptions")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        if (opt.isEmpty())
        {
            logger.info("Created success message for new project...sending response back...");
            return ResponseEntity.status(HttpStatus.CREATED).body("{}");
        }
        else{

            logger.info("Error code >>>>>>>>>> " + opt.get());
            err =Json.createObjectBuilder()
                .add("message","Failed to create new Project")
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
        
        
    }

    @GetMapping(path="/delete/{id}")
    public ResponseEntity<String> deleteProjectbyID(@PathVariable String id){
        logger.info("Project id : "+id);
        
        JsonObject err;

        
        try {
            
            if(projectRepository.deleteProject(id)){

                logger.info("Project deleted status ON updated in SQL");
            }else{
                err =Json.createObjectBuilder()
                    .add("message","Failed to delete Project")
                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
            }
        } catch (Exception e) {
            err =Json.createObjectBuilder()
            .add("message","Failed to retrieve Project due to SQL Exceptions")
            .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
        }
        
        
        logger.info("Project deleted successfully");

        return ResponseEntity.ok("{}");  
    }


    @GetMapping(path="/{id}")
    public ResponseEntity<String> getProject(@PathVariable String id){
        
        logger.info("Project id : "+id);
        
        Project project;
        JsonObject err;

        
        try {
            
            project = projectRepository.getProject(id);
            logger.info("Project extracted from SQL");
        } catch (Exception e) {
            err =Json.createObjectBuilder()
            .add("message","Failed to retrieve Project due to SQL Exceptions")
            .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
        }
        
        
        logger.info("Project sent to front end successfully");

        return ResponseEntity.ok(project.toJSON().toString());  
    }

    
    

    

   
}
