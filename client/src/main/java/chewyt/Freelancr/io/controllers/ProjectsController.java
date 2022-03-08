package chewyt.Freelancr.io.controllers;


import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chewyt.Freelancr.io.models.Project;
import chewyt.Freelancr.io.repositories.ProjectRepository;
import chewyt.Freelancr.io.repositories.UserRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectsController {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepository;
    
    private final Logger logger = Logger.getLogger(ProjectsController.class.getName());
    
    // private Optional<Integer> opt;
    // private Optional<Project> optProject;
    private List<Project> projects = new LinkedList<>();
    
    @GetMapping
    public ResponseEntity<String> getProjects(){

        projects = projectRepo.getProjects();
        List<JsonObject> jprojects = projects.stream()
				.map(p -> p.toJSON())
				.toList();
        
        return ResponseEntity.ok(Json.createArrayBuilder(jprojects).build().toString());
    }

    @GetMapping(path = "/count")
    public ResponseEntity<String> getProjectCount(){

        int projectCount = projectRepo.getProjectCount();
        JsonObject count = Json.createObjectBuilder()
            .add("count", projectCount)
            .build();
        System.out.println("Project count done");
        return ResponseEntity.ok(count.toString());
    }

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<String> getMyProjects(@PathVariable String user_id){

        logger.info("User ID>>>>>>>>>>"+user_id);

        // check user type by user id
        String usertype = userRepository.checkType(user_id);
        
        if (usertype.equals("freelancer")) {
            projects = projectRepo.getMyProjectsFreelancer(user_id);
        }else if (usertype.equals("client")){
            projects = projectRepo.getMyProjectsClient(user_id);
        }else{
            return ResponseEntity.badRequest().build();
        }
        
        List<JsonObject> jprojects = projects.stream()
				.map(p -> p.toJSON())
				.toList();
        
        return ResponseEntity.ok(Json.createArrayBuilder(jprojects).build().toString());
    }
   
    
}
