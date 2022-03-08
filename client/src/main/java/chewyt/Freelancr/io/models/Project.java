package chewyt.Freelancr.io.models;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
// import java.text.SimpleDateFormat;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Date;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Project {
    private String project_id; // auto uuid and sha1
    private String project_title;
    private String project_brief;
    private String project_specialties; //checkbox selection
    private Budget project_budget_range; //enum selection

    private boolean project_status; //true for OPEN, false for CLOSED
    
    private String project_posted_date;
    private boolean project_display; //if deleted turn to false

    //foreign key
    private String project_client_id;
    private String project_client_username;
    private String project_freelancer_id;
    private String project_smart_contract_id;

    //Message owner button press to finalize price
    private int project_cost; //type decimal


    //after  String project completion
    private Date project_end_date;
    private int project_rating;  // 1 to 5 stars
    private String project_reviews;
    
    public Project() {
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getProject_brief() {
        return project_brief;
    }

    public void setProject_brief(String project_brief) {
        this.project_brief = project_brief;
    }

    public String getProject_specialties() {
        return project_specialties;
    }

    public void setProject_specialties(String project_specialties) {
        this.project_specialties = project_specialties;
    }

    public Budget getProject_budget_range() {
        return project_budget_range;
    }

    public void setProject_budget_range(Budget project_budget_range) {
        this.project_budget_range = project_budget_range;
    }

    public boolean isProject_status() {
        return project_status;
    }

    public void setProject_status(boolean project_status) {
        this.project_status = project_status;
    }

    public String getProject_client_username() {
        return project_client_username;
    }

    public void setProject_client_username(String project_client_username) {
        this.project_client_username = project_client_username;
    }

    public boolean isProject_display() {
        return project_display;
    }

    public String getProject_posted_date() {
        return project_posted_date;
    }

    public void setProject_posted_date(String project_posted_date) {
        this.project_posted_date = project_posted_date;
    }

    public void setProject_display(boolean project_display) {
        this.project_display = project_display;
    }

    public String getProject_client_id() {
        return project_client_id;
    }

    public void setProject_client_id(String project_client_id) {
        this.project_client_id = project_client_id;
    }

    public String getProject_freelancer_id() {
        return project_freelancer_id;
    }

    public void setProject_freelancer_id(String project_freelancer_id) {
        this.project_freelancer_id = project_freelancer_id;
    }

    public String getProject_smart_contract_id() {
        return project_smart_contract_id;
    }

    public void setProject_smart_contract_id(String project_smart_contract_id) {
        this.project_smart_contract_id = project_smart_contract_id;
    }

    public int getProject_cost() {
        return project_cost;
    }

    public void setProject_cost(int project_cost) {
        this.project_cost = project_cost;
    }

    public Date getProject_end_date() {
        return project_end_date;
    }

    public void setProject_end_date(Date project_end_date) {
        this.project_end_date = project_end_date;
    }

    public int getProject_rating() {
        return project_rating;
    }

    public void setProject_rating(int project_rating) {
        this.project_rating = project_rating;
    }

    public String getProject_reviews() {
        return project_reviews;
    }

    public void setProject_reviews(String project_reviews) {
        this.project_reviews = project_reviews;
    }

    public static Project create(String jsonString) throws ParseException {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonString.getBytes()));
        JsonObject o = reader.readObject();
        final Project project = new Project();
        try {
            project.project_id = o.getString("project_id");
            System.out.println("Ok processed");
        } catch (Exception e) {
        }
        project.project_title = o.getString("project_title");
        // System.out.println("Ok processed2");
        project.project_brief = o.getString("project_brief");
        // System.out.println("Ok processed2");

        project.project_specialties=o.getJsonArray("project_specialties").toString();
        
        // System.out.println("Ok processed3");
        project.project_budget_range = Enum.valueOf(Budget.class, o.getString("project_budget_range"));
        // System.out.println("Ok processed4");
        project.project_status = o.getBoolean("project_status");
        project.project_display = o.getBoolean("project_display");
        // System.out.println("Ok processed4");
        project.project_posted_date= o.getString("project_posted_date");
        // System.out.println("Ok processed4");
        project.project_client_id = o.getString("project_client_id");
        project.project_cost = o.getInt("project_cost");
        // System.out.println("Ok processed5");

        return project;
    }

    public static Project create(SqlRowSet rs){
        Project project = new Project();
        System.out.println("Rowset column count"+rs.getMetaData().getColumnCount());
        
        project.project_id=rs.getString("project_id");        
        project.project_title=rs.getString("project_title");        
        project.project_brief=rs.getString("project_brief");        
        project.project_specialties=rs.getString("project_specialties");        
        project.project_budget_range=Enum.valueOf(Budget.class, rs.getString("project_budget_range")) ;        
        project.project_posted_date=rs.getString("project_posted_date");        
        project.project_status = rs.getBoolean("project_status");   
        project.project_client_username = rs.getString("username");
        project.project_cost=rs.getInt("project_cost");   
        project.project_display=rs.getBoolean("project_display");   
        // project.project_freelancer_id = rs.getString("project_freelancer_id");
        // project.project_smart_contract_id = rs.getString("project_smart_contract_id");
        
        return project;
    }
   
    

    public JsonObject toJSON() {
        JsonObject obj = Json.createObjectBuilder()
                .add("project_id", this.project_id)
                .add("project_title", this.project_title)
                .add("project_brief", this.project_brief)
                .add("project_specialties", this.project_specialties)
                .add("project_budget_range", this.project_budget_range.toString())
                .add("project_posted_date", this.project_posted_date)
                .add("project_status", this.project_status)
                .add("username", this.project_client_username)
                .add("project_cost",this.project_cost)
                .add("project_display",this.project_display)
                // .add("project_freelancer_id",this.project_freelancer_id)
                // .add("project_smart_contract_id",this.project_smart_contract_id)
                .build();
        return obj;
    }
    
    
}
