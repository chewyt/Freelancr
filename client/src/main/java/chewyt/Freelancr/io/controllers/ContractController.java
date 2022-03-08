package chewyt.Freelancr.io.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chewyt.Freelancr.io.models.Contract;
import chewyt.Freelancr.io.repositories.ContractRepository;
import chewyt.Freelancr.io.repositories.ProjectRepository;
import chewyt.Freelancr.io.repositories.UserRepository;
import chewyt.Freelancr.io.services.ContractService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractController {

    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContractService contractService;
    
    private final Logger logger = Logger.getLogger(ContractController.class.getName());

    @PostMapping(path = "/contract", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createProjectContract(@RequestBody MultiValueMap<String, String> form){

        String client_id = form.getFirst("client_id");
        String freelancer_id = form.getFirst("freelancer_id");
        String smart_contract_id = form.getFirst("smart_contract_id");
        String project_id = form.getFirst("project_id");
        Double project_cost = Double.parseDouble(form.getFirst("project_cost"));

        logger.info("Client ID from POST >>>>> %s".formatted(client_id));
        logger.info("Freelancer ID from POST >>>>> %s".formatted(freelancer_id));
        logger.info("Smart contract ID from POST >>>>> %s".formatted(smart_contract_id));
        logger.info("Project ID from POST >>>>> %s".formatted(project_id));
        logger.info("Project Cost from POST >>>>> %.2f".formatted(project_cost));


        //run a Transactional service to update project and add a new record in contract table as transaction

        boolean hasUpdated;
        // boolean hasCreated;
        // Contract contract =new Contract();

        try {
            hasUpdated = projectRepository.updateProject(freelancer_id,smart_contract_id,project_cost,project_id);
        } catch (Exception ex) {
            JsonObject err = Json.createObjectBuilder()
            .add("message", ex.getMessage())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }

        if (hasUpdated) {
            
            // contract.setProject_smart_contract_id(smart_contract_id); 
            // contract.setClient_id(client_id); 
            // contract.setFreelancer_id(freelancer_id); 
            // contract.setProject_id(project_id); 
            // contract.setProject_cost(project_cost); 
            // contract.setStatus("funded");

            
            // try {
            //     hasCreated=contractRepository.addContract(contract);
            // } catch (Exception ex) {
            //     JsonObject err = Json.createObjectBuilder()
            //     .add("message", ex.getMessage())
            //     .build();
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
            // }
            // if(hasCreated){

                return ResponseEntity.ok("{}");
            // }else{

            //     return ResponseEntity.badRequest().build();
            // }
        
        } else {
            return ResponseEntity.badRequest().build();
        }


    }
    

    @PostMapping(path = "/contract-test", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> createProjectContractTest(@RequestBody MultiValueMap<String, String> form){

        String client_id = form.getFirst("client_id");
        String freelancer_id = form.getFirst("freelancer_id");
        String smart_contract_id = form.getFirst("smart_contract_id");
        String project_id = form.getFirst("project_id");
        Double project_cost = Double.parseDouble(form.getFirst("project_cost"));

        logger.info("Client ID from POST >>>>> %s".formatted(client_id));
        logger.info("Freelancer ID from POST >>>>> %s".formatted(freelancer_id));
        logger.info("Smart contract ID from POST >>>>> %s".formatted(smart_contract_id));
        logger.info("Project ID from POST >>>>> %s".formatted(project_id));
        logger.info("Project Cost from POST >>>>> %.2f".formatted(project_cost));

        Contract contract = new Contract();

        contract.setProject_smart_contract_id(smart_contract_id); 
        contract.setClient_id(client_id); 
        contract.setFreelancer_id(freelancer_id); 
        contract.setProject_id(project_id); 
        contract.setProject_cost(project_cost); 
        contract.setStatus("funded");

        //run a Transactional service to update project and add a new record in contract table as transaction

        try {   
                //This being a void method, Rollback auto done if there is exception
                contractService.updateProjectAddContract(contract);
        } catch (Exception ex) {
            JsonObject err = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
        }
        return ResponseEntity.ok("{}");
    }



    @GetMapping(path="/contract/{project_id}/{user_id}")
    public ResponseEntity<String> getContract(@PathVariable String project_id, @PathVariable String user_id){
        
        logger.info("Project id : "+project_id);
        logger.info("User id : "+user_id);
        
        Contract contract;
        JsonObject err;

        // check user type by user id
        String usertype = userRepository.checkType(user_id);
        
        try {
            
            if (usertype.equals("freelancer")) {
                contract = projectRepository.getContractFreelancer(project_id);
            }else if (usertype.equals("client")){
                contract = projectRepository.getContractClient(project_id);
            }else{
                return ResponseEntity.badRequest().build();
            }
            
            logger.info("Contract extracted from SQL");
        } catch (Exception e) {
            err =Json.createObjectBuilder()
            .add("message","Failed to retrieve Contract due to SQL Exceptions")
            .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
        }
        
        
        
        logger.info("Contract sent to front end successfully");

        return ResponseEntity.ok(contract.toContractJSON().toString());  
    }

    @GetMapping(path = "/contract-update/{project_id}/{contract_status}")
    public ResponseEntity<String> updateContractStatus (@PathVariable String project_id, @PathVariable String contract_status){
        
        logger.info("Project id : "+project_id);
        logger.info("Contract status: "+contract_status);

        JsonObject err;
        try {
            
            if(contractRepository.updateContractStaus(project_id,contract_status)){

                logger.info("Contract status updated in SQL");
            }else{
                err =Json.createObjectBuilder()
                    .add("message","Failed to update Contract status")
                    .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.toString());
            }
        } catch (Exception e) {
            err =Json.createObjectBuilder()
            .add("message","Failed to update Contract status due to SQL Exceptions")
            .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err.toString());
        }
        
        
        logger.info("Contract status updated successfully");

        return ResponseEntity.ok("{}");  
        
    }

}
