package chewyt.Freelancr.io.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import chewyt.Freelancr.io.models.Finterest;

import static chewyt.Freelancr.io.repositories.SQL.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class FinterestRepository {
    
    @Autowired
    private JdbcTemplate template;
    
   
    public boolean addFinterest(Finterest f){
        System.out.println("adding User in SQL...");
        return template.update(SQL_INSERT_FINTEREST,
            f.getFinterest_id(),
            f.getFinterest_project_id(),
            f.getFinterest_freelancer_id(),
            f.getFinterest_comments(),
            f.getFinterest_budget(),
            f.isFinterest_status())
            >0;
    }

    public boolean hasUser(Finterest f){
        final SqlRowSet rs = template.queryForRowSet(SQL_AUTH_FINTEREST,f.getFinterest_project_id(),f.getFinterest_freelancer_id());
        if (rs.next()) {
            System.out.println("fin count = "+rs.getInt("fin_count"));
            System.out.println(rs.getInt("fin_count")>0);
            return rs.getInt("fin_count")>0;
        }
        return false;
    }
    
    // SQL_AUTH_FINTEREST

    // public User getUser(String email, String password){
    //     System.out.println("Retreiving user data....");
    //     final SqlRowSet rs = template.queryForRowSet(SQL_GET_USER,email,password);
    //     rs.next();
    //     final User user = User.create(rs);
    //     System.out.println("User retrieved....back to service");  
    //     return user;
    // }
    
    public List<Finterest> getFinterests(String project_id){

        List<Finterest> finterests = new LinkedList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_FINTEREST_BY_ID,project_id);
        System.out.println("Retreiving finterests based on project id for client...");
        while (rs.next()) {
            finterests.add(Finterest.create(rs));
        }
        return finterests;
    }

}
