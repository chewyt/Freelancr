package chewyt.Freelancr.io.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import chewyt.Freelancr.io.models.Contract;

import static chewyt.Freelancr.io.repositories.SQL.*;

import java.util.logging.Logger;


@Repository
public class ContractRepository {
    
    @Autowired
    private JdbcTemplate template;
    
   private final Logger logger = Logger.getLogger(ContractRepository.class.getName());

    public boolean addContract(Contract c){
        logger.info("adding Contract in SQL...");
        logger.info("confirming values for record insertion...");
        
        logger.info("project_smart_contract_id: "+c.getProject_smart_contract_id());
        logger.info("freelancer_id: "+c.getFreelancer_id());
        logger.info("client_id: "+c.getClient_id());
        logger.info("project_id: "+c.getProject_id());
        logger.info("project_cost: "+c.getProject_cost());
        logger.info("contract_status: "+c.getStatus());
        
        
        
        return template.update(SQL_INSERT_CONTRACT,
            c.getProject_smart_contract_id(),
            c.getFreelancer_id(),
            c.getClient_id(),
            c.getProject_id(),
            c.getProject_cost())
            >0;
    }
    public boolean updateContractStaus(String project_id,String contract_status){
        logger.info("updating Contract  status in SQL...");
        return template.update(SQL_UPDATE_CONTRACT_STATUS,
            contract_status,project_id)
            >0;
    }



    

}
