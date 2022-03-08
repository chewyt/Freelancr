package chewyt.Freelancr.io.models;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Finterest {
    
    private String finterest_id;
    private String finterest_project_id;
    private String finterest_freelancer_id;
    private String finterest_comments;
    private Double finterest_budget;
    private boolean finterest_status;

    private String finterest_freelancer_username;

    
    public String getFinterest_freelancer_username() {
        return finterest_freelancer_username;
    }



    public void setFinterest_freelancer_username(String finterest_freelancer_username) {
        this.finterest_freelancer_username = finterest_freelancer_username;
    }



    public Finterest() {
    }

    

    public String getFinterest_id() {
        return finterest_id;
    }



    public void setFinterest_id(String finterest_id) {
        this.finterest_id = finterest_id;
    }



    public String getFinterest_project_id() {
        return finterest_project_id;
    }



    public void setFinterest_project_id(String finterest_project_id) {
        this.finterest_project_id = finterest_project_id;
    }



    public String getFinterest_freelancer_id() {
        return finterest_freelancer_id;
    }



    public void setFinterest_freelancer_id(String finterest_freelancer_id) {
        this.finterest_freelancer_id = finterest_freelancer_id;
    }



    public String getFinterest_comments() {
        return finterest_comments;
    }



    public void setFinterest_comments(String finterest_comments) {
        this.finterest_comments = finterest_comments;
    }



    public Double getFinterest_budget() {
        return finterest_budget;
    }



    public void setFinterest_budget(Double finterest_budget) {
        this.finterest_budget = finterest_budget;
    }



    public boolean isFinterest_status() {
        return finterest_status;
    }



    public void setFinterest_status(boolean finterest_status) {
        this.finterest_status = finterest_status;
    }



    public static Finterest create(String jsonString) throws ParseException {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonString.getBytes()));
        JsonObject o = reader.readObject();
        final Finterest finterest = new Finterest();
        try {
            finterest.finterest_id = o.getString("finterest_id");
            System.out.println("Ok processed");
        } catch (Exception e) {
            System.out.println("Cannot get finterest ID?");
        }
        finterest.finterest_project_id = o.getString("finterest_project_id");

        finterest.finterest_freelancer_id = o.getString("finterest_freelancer_id");
        System.out.println("Ok processed2");
        finterest.finterest_comments = o.getString("finterest_comments");
        System.out.println("Ok processed2");
        finterest.finterest_budget =(double) o.getInt("finterest_budget");
        System.out.println("Ok processed3");
        finterest.finterest_status = o.getBoolean("finterest_status");
       
        return finterest;
    }

    // public static Finterest extractCredentials(String jsonString) throws ParseException {
    //     JsonReader reader = Json.createReader(
    //             new ByteArrayInputStream(jsonString.getBytes()));
    //     JsonObject o = reader.readObject();
    //     final Finterest user = new Finterest();
    //     try {
    //         user.email = o.getString("email");
    //         user.password = o.getString("password");
    //         // System.out.println("Ok processed");
    //     } catch (Exception e) {
    //     }
       
    //     return user;
    // }


    public static Finterest create(SqlRowSet rs){
        Finterest finterest = new Finterest();
        
        finterest.finterest_id=rs.getString("finterest_id");        
        finterest.finterest_project_id=rs.getString("finterest_project_id");        
        finterest.finterest_freelancer_id=rs.getString("finterest_freelancer_id");        
        finterest.finterest_comments=rs.getString("finterest_comments");        
        finterest.finterest_budget=rs.getDouble("finterest_budget");        
        finterest.finterest_status=rs.getBoolean("finterest_status");        
        finterest.finterest_freelancer_username=rs.getString("username");        
        
        return finterest;
    }

    public JsonObject toJSON() {
        JsonObject obj = Json.createObjectBuilder()
                .add("finterest_id", this.finterest_id)
                .add("finterest_project_id", this.finterest_project_id)
                .add("finterest_freelancer_id", this.finterest_freelancer_id)
                .add("finterest_comments", this.finterest_comments)
                .add("finterest_budget", this.finterest_budget)
                .add("finterest_status", this.finterest_status)
                .add("finterest_freelancer_username", this.finterest_freelancer_username)
                .build();
        return obj;
    }
    
}
