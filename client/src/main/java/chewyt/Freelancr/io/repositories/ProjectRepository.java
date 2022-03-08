package chewyt.Freelancr.io.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import chewyt.Freelancr.io.models.Contract;
import chewyt.Freelancr.io.models.Project;

import static chewyt.Freelancr.io.repositories.SQL.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class ProjectRepository {
    
    @Autowired
    private JdbcTemplate template;
    
    

    public boolean addProject(Project p){
        System.out.println("adding Project in SQL...");
        return template.update(SQL_INSERT_PROJECT,
            p.getProject_id(),
            p.getProject_title(),
            p.getProject_brief(),
            p.getProject_specialties(),
            p.getProject_budget_range().toString(),
            p.isProject_status(),
            p.getProject_posted_date().toString(),
            p.isProject_display(),
            p.getProject_client_id(),
            p.getProject_cost())

            >0;
    }
    
   
    public List<Project> getProjects(){

        List<Project> projects = new LinkedList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_PROJECTS);
        System.out.println("Retreiving all latest projects with limit of 20");
        while (rs.next()) {
            projects.add(Project.create(rs));
        }
        return projects;
    }

    public List<Project> getMyProjectsClient(String user_id){

        List<Project> projects = new LinkedList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_MY_PROJECTS_CLIENT,user_id);
        System.out.println("Retreiving all my latest projects as client...");
        while (rs.next()) {
            projects.add(Project.create(rs));
        }
        return projects;
    }
    
    public List<Project> getMyProjectsFreelancer(String user_id){

        List<Project> projects = new LinkedList<>();
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_ALL_MY_PROJECTS_FL,user_id);
        System.out.println("Retreiving all my latest projects as freelancer...");
        while (rs.next()) {
            projects.add(Project.create(rs));
        }
        return projects;
    }
    
    public int getProjectCount(){

        
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_TODAY_PROJECT_COUNT);
        System.out.println("Retreiving today's posted project count...");
        rs.next();

        return rs.getInt("today_count");
    }

    public Project getProject(String id){

        Project project;
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_PROJECT_BY_ID,id);
        System.out.println("Retreiving project...");
        rs.next();
        project=Project.create(rs);
        return project;
    }

    public Contract getContractFreelancer(String id){

        Contract contract;
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_CONTRACT_BY_ID_FREELANCE,id);
        System.out.println("Retreiving contract as freelancer...");
        rs.next();
        contract=Contract.createContract(rs);
        
        System.out.println("Contract object created from rowset from freelancer request.");
        return contract;
    }
    public Contract getContractClient(String id){
        
        Contract contract;
        final SqlRowSet rs = template.queryForRowSet(SQL_GET_CONTRACT_BY_ID_CLIENT,id);//SQL_GET_CONTRACT_BY_ID_CLIENT
        System.out.println("Retreiving contract as client...");
        // rs.first();
        rs.next();
        contract=Contract.createContract(rs);
        System.out.println("Contract object created from rowset from client request.");
        return contract;
    }
    
    public boolean deleteProject(String id){
        System.out.println("Deleting project...");
        return template.update(SQL_DELETE_PROJECT,id)>0;
    }
    
    public boolean updateProject(String freelancer_id, String smart_contract_id,Double project_cost,String project_id){
        System.out.println("Updating project...");
        return template.update(SQL_UPDATE_PROJECT,freelancer_id,smart_contract_id,project_cost,project_id)>0;
    }

}
