package chewyt.Freelancr.io.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chewyt.Freelancr.io.models.Contract;
import chewyt.Freelancr.io.repositories.ContractRepository;
import chewyt.Freelancr.io.repositories.ProjectRepository;

@Service
public class ContractService {
    
    private final Logger logger = Logger.getLogger(ContractService.class.getName());

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ContractRepository contractRepository;


    @Transactional(rollbackFor = Exception.class)
    public void updateProjectAddContract(Contract contract) throws Exception{

        // TRANSACTIONAL BOUNDARY
        logger.info("Entering transactional boundary");
        if (!(projectRepository.updateProject
        (contract.getFreelancer_id(),contract.getProject_smart_contract_id(),
        contract.getProject_cost(),contract.getProject_id()))){
            logger.warning("Failure: Performing transactional rollback");
            throw new Exception("Cannot perform transaction");
        }else{
            logger.info("Successs: Updated existing project record in projects table");
        }
        
        if (!(contractRepository.addContract(contract))){
            logger.warning("Failure: Performing transactional rollback");
            throw new Exception("Cannot perform transaction");
        }else{
            logger.info("Successs: Created a record in contract table");
        }
        
        logger.info("Leaving transactional boundary");
        // TRANSACTIONAL BOUNDARY
    }

}
