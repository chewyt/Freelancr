package chewyt.Freelancr.io.models;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class User {
    
    private String user_id;
    private String name;
    private String username;
    private String email;
    private String password;

    private String location;
    private String bio;
    // private String profile_id;
    private Type type ;
    
    public User() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static User create(String jsonString) throws ParseException {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonString.getBytes()));
        JsonObject o = reader.readObject();
        final User user = new User();
        try {
            user.user_id = o.getString("user_id");
            // System.out.println("Ok processed");
        } catch (Exception e) {
        }
        user.name = o.getString("name");
        // System.out.println("Ok processed2");
        user.username = o.getString("username");
        // System.out.println("Ok processed2");
        user.email = o.getString("email");
        // System.out.println("Ok processed3");
        user.password = o.getString("password");
        // System.out.println("Ok processed4");
        user.location = o.getString("location");
        // System.out.println("Ok processed4");
        user.bio = o.getString("bio");
        // System.out.println("Ok processed4");
        user.type = Enum.valueOf(Type.class, o.getString("type"));
        // System.out.println("Ok processed5");

        return user;
    }

    public static User extractCredentials(String jsonString) throws ParseException {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonString.getBytes()));
        JsonObject o = reader.readObject();
        final User user = new User();
        try {
            user.email = o.getString("email");
            user.password = o.getString("password");
            // System.out.println("Ok processed");
        } catch (Exception e) {
        }
       
        return user;
    }


    public static User create(SqlRowSet rs){
        User user = new User();
        
        user.user_id=rs.getString("user_id");        
        user.username=rs.getString("username");        
        user.name=rs.getString("name");        
        user.email=rs.getString("email");        
        user.bio=rs.getString("bio");        
        user.location=rs.getString("location");        
        user.type = Enum.valueOf(Type.class, rs.getString("type"));      
        
        return user;
    }
    public JsonObject toJSON() {
        JsonObject obj = Json.createObjectBuilder()
                .add("user_id", this.user_id)
                .add("username", this.username)
                .add("name", this.name)
                .add("email", this.email)
                .add("bio", this.bio)
                .add("location", this.location)
                .add("type", this.type.toString())
                .build();
        return obj;
    }
    
}
