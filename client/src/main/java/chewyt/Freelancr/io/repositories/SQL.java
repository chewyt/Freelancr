package chewyt.Freelancr.io.repositories;

public interface SQL {
    
    // public static final String SQL_GET_ALL_TASKS = 
    // "select * from task order by username";

    public static final String SQL_GET_USERNAME_COUNT =
    "select count(*) as user_count from user where email = ?";
    
    public static final String SQL_GET_USERNAME_COUNT_BY_USERNAME =
    "select count(*) as user_count from user where username = ?";
    
    // public static final String SQL_INSERT_TASK =
    // "insert into task (username, task_name, priority, due_date) values(?,?,?,?)";
    
    public static final String SQL_INSERT_USER =
    "insert into user(user_id,name,username,password,email,bio,location,type,auth_token) values(?,?,?,sha1(?),?,?,?,?,?)";
    
    public static final String SQL_AUTH_USER =
    "select count(*) as user_count from user where email = ? and password = sha1(?)";
    
    public static final String SQL_GET_USER =
    "select * from user where email = ? and password = sha1(?)";
    
    public static final String SQL_INSERT_PROJECT =
    "insert into projects(project_id,project_title,project_brief,project_specialties,project_budget_range,project_status,project_posted_date,project_display, project_client_id,project_cost)values(?,?,?,?,?,?,?,?,?,?)";
    
    public static final String SQL_INSERT_CONTRACT =
    "insert into contracts(project_smart_contract_id,freelancer_id,client_id,project_id,project_cost,contract_status) 	values(?,?,?,?,?,'funded')";
    
    public static final String SQL_GET_ALL_PROJECTS = 
    "select p.*,u.username    from projects as p      left join user as u      on p.project_client_id = u.user_id     where project_status=1 and project_display=1        order by project_posted_date desc  ;";
    // "select p.project_id,p.project_title,p.project_brief,p.project_specialties,p.project_budget_range, p.project_posted_date,p.project_client_id,p.project_status,u.username,p.project_cost , p.project_display     from projects as p      left join user as u      on p.project_client_id = u.user_id     where project_status=1 and project_display=1        order by project_posted_date desc   limit 20;";
    
    public static final String SQL_GET_ALL_MY_PROJECTS_CLIENT = 
    // "select p.*,u.username    from projects as p      left join user as u      on p.project_client_id = u.user_id     where project_status=1 and project_display=1 and  project_client_id=?     order by project_posted_date desc   limit 20;";
    "select p.project_id,p.project_title,p.project_brief,p.project_specialties,p.project_budget_range, p.project_posted_date,p.project_client_id,p.project_status,u.username,p.project_cost, p.project_display    from projects as p      left join user as u      on p.project_client_id = u.user_id     where  project_display=1 and  project_client_id=?     order by project_posted_date desc  ;";
    
    public static final String SQL_GET_ALL_MY_PROJECTS_FL = 
    // "select p.*,u.username     from projects as p      left join user as u      on p.project_client_id = u.user_id     where project_status=1 and project_display=1 and  project_freelancer_id=?     order by project_posted_date desc   limit 20;";
    "select p.project_id,p.project_title,p.project_brief,p.project_specialties,p.project_budget_range, p.project_posted_date,p.project_client_id,p.project_status,u.username,p.project_cost, p.project_display     from projects as p      left join user as u      on p.project_client_id = u.user_id     where project_status=0 and project_display=1 and  project_freelancer_id=?     order by project_posted_date desc  ;";
    
    public static final String SQL_GET_PROJECT_BY_ID = 
    // " select p.*,u.username	from projects as p    left join user as u    on p.project_client_id = u.user_id    where project_id=?";
    " select p.project_id,p.project_title,p.project_brief,p.project_specialties,p.project_budget_range, p.project_posted_date,p.project_client_id,p.project_status,u.username,p.project_cost, p.project_display	from projects as p    left join user as u    on p.project_client_id = u.user_id    where project_id=?";
    
    public static final String SQL_GET_CONTRACT_BY_ID_FREELANCE = 
    " select p.project_smart_contract_id,u.username,p.project_cost,p.project_reviews	from projects as p    left join user as u    on p.project_client_id = u.user_id    where project_id=?";
    
    public static final String SQL_GET_CONTRACT_BY_ID_CLIENT = 
    " select p.project_smart_contract_id,u.username,p.project_cost,p.project_reviews	from projects as p    left join user as u    on p.project_freelancer_id = u.user_id    where project_id=?";
    
    public static final String SQL_GET_TODAY_PROJECT_COUNT = 
    "select count(*) as today_count	from projects    where project_status=1 and project_display=1 and DATE(project_posted_date) - curdate() =1";
   
    public static final String SQL_DELETE_PROJECT = 
    " UPDATE `projects` SET `project_display` = '0' WHERE (`project_id` = ?)";
    
    public static final String SQL_UPDATE_PROJECT = 
    " UPDATE `freelancr`.`projects` SET `project_status` = '0',`project_reviews` = 'funded', `project_freelancer_id` = ?, `project_smart_contract_id` = ?, `project_cost` = ? WHERE (`project_id` = ?)";
    
    public static final String SQL_UPDATE_CONTRACT_STATUS = 
    " UPDATE `freelancr`.`projects` SET `project_reviews` = ? WHERE (`project_id` = ?)";
    
    public static final String SQL_GET_USER_CRED =
    "select * from user where auth_token =?";
    
    public static final String SQL_GET_USER_TYPE =
    "select type from user where user_id =?";
    
    public static final String SQL_INSERT_FINTEREST =
    "insert into finterest(finterest_id,finterest_project_id,finterest_freelancer_id,finterest_comments,finterest_budget,finterest_status) values(?,?,?,?,?,?)";
    
    public static final String SQL_AUTH_FINTEREST =
    "select count(*) as fin_count from finterest where finterest_project_id = ? and finterest_freelancer_id = ?";
    
    public static final String SQL_GET_ALL_FINTEREST_BY_ID =
    "select * from finterest as f	left join user as u    on f.finterest_freelancer_id = u.user_id where finterest_project_id = ?";
}
