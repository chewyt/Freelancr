package chewyt.Freelancr.io.models;


import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;


public class Contract {
    private String project_smart_contract_id;
    private String username;
    private String freelancer_id;
    private String client_id;
    private String project_id;
    private Double project_cost;
    private String status;
    
    public String getProject_smart_contract_id() {
        return project_smart_contract_id;
    }
    public void setProject_smart_contract_id(String project_smart_contract_id) {
        this.project_smart_contract_id = project_smart_contract_id;
    }
    
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Double getProject_cost() {
        return project_cost;
    }
    public void setProject_cost(Double project_cost) {
        this.project_cost = project_cost;
    }
    public String getFreelancer_id() {
        return freelancer_id;
    }
    public void setFreelancer_id(String freelancer_id) {
        this.freelancer_id = freelancer_id;
    }
    public String getClient_id() {
        return client_id;
    }
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    public String getProject_id() {
        return project_id;
    }
    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public static Contract createContract(SqlRowSet rs){
        System.out.println("Processing create Contract 1");
        Contract contract = new Contract();
        System.out.println("Processing create Contract 2");
        
        System.out.println("Rowset column count"+rs.getMetaData().getColumnCount());
        System.out.println(rs.getRow());
        // rs.next();
        // System.out.println(rs.getRow());
        System.out.println("Processing create Contract 2.5");
        contract.project_smart_contract_id=rs.getString("project_smart_contract_id");        
        System.out.println("Processing create Contract 3");
        contract.username = rs.getString("username");
        System.out.println("Processing create Contract 4");
        contract.project_cost=rs.getBigDecimal("project_cost").doubleValue() ;   
        System.out.println("Processing create Contract 5");
        contract.status=rs.getString("project_reviews");     //temp as status for project progress    
        System.out.println("Processing create Contract 6");
        
        
        return contract;
    }

    public JsonObject toContractJSON() {
        JsonObject obj = Json.createObjectBuilder()
                .add("smart_contract_id", this.project_smart_contract_id)
                .add("username", this.username)
                .add("project_cost",this.project_cost)
                .add("status", this.status)
                .build();
        return obj;
    }

    // public static Contract create(String jsonString) throws ParseException {
    //     JsonReader reader = Json.createReader(
    //             new ByteArrayInputStream(jsonString.getBytes()));
    //     JsonObject o = reader.readObject();
    //     final Contract contract = new Contract();
    //     try {
    //         contract.project_smart_contract_id = o.getString("contract_id");
    //         System.out.println("Ok processed");
    //     } catch (Exception e) {
    //         System.out.println("Cannot get contract ID?");
    //     }
    //     contract.contract_project_id = o.getString("contract_project_id");

    //     contract.contract_freelancer_id = o.getString("contract_freelancer_id");
    //     System.out.println("Ok processed2");
    //     contract.contract_comments = o.getString("contract_comments");
    //     System.out.println("Ok processed2");
    //     contract.contract_budget =(double) o.getInt("contract_budget");
    //     System.out.println("Ok processed3");
    //     contract.contract_status = o.getBoolean("contract_status");
       
    //     return contract;
    // }
    
}
