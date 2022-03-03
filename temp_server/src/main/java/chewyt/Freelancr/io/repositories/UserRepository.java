package chewyt.Freelancr.io.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import chewyt.Freelancr.io.models.User;

import static chewyt.Freelancr.io.repositories.SQL.*;

@Repository
public class UserRepository {
    
    @Autowired
    private JdbcTemplate template;
    
    public boolean hasUser(String user_email){
        System.out.println("Email:   "+user_email);
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_USERNAME_COUNT,user_email);
        if (rs.next()) {
            System.out.println("User count = "+rs.getInt("user_count"));
            System.out.println(rs.getInt("user_count")>0);
            return rs.getInt("user_count")>0;
        }
        return false;
    }

    public boolean addUser(User u){
        System.out.println("adding User in SQL...");
        return template.update(SQL_INSERT_USER,
            u.getUser_id(),
            u.getName(),
            u.getUsername(),
            u.getPassword(),
            u.getEmail(),
            u.getBio(),
            u.getLocation(),
            u.getType().toString())
            >0;
    }
    // public boolean addUserList(User u){
    //     System.out.println("adding userlist in SQL...");
    //     return template.update(SQL_INSERT_USER_LIST,
    //         u.getUsername(),
    //         u.getPassword())
    //         >0;
    // }

    public boolean authUser(String email, String password){
        System.out.println("authenticating...");
        final SqlRowSet rs = template.queryForRowSet(SQL_AUTH_USER,email,password);
        if (rs.next()) {
            System.out.println("User count = "+rs.getInt("user_count"));
            System.out.println(rs.getInt("user_count")>0);
            return rs.getInt("user_count")>0;
        }
        return false;
    }

    public User getUser(String email, String password){
        System.out.println("Retreiving user data....");
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_USER,email,password);
        rs.next();
        final User user = User.create(rs);
        System.out.println("User retrieved....back to service");  
        return user;
    }
    

}
