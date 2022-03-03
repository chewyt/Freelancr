package chewyt.Freelancr.io.repositories;

public interface SQL {
    
    public static final String SQL_GET_ALL_TASKS = 
    "select * from task order by username";

    public static final String SQL_GET_USERNAME_COUNT =
    "select count(*) as user_count from user where email = ?";
    
    public static final String SQL_INSERT_TASK =
    "insert into task (username, task_name, priority, due_date) values(?,?,?,?)";
    
    public static final String SQL_INSERT_USER =
    "insert into user(user_id,name,username,password,email,bio,location,type) values(?,?,?,sha1(?),?,?,?,?)";
    
    
    // public static final String SQL_INSERT_USER_LIST =
    // "insert into user(username, password) values(?,sha1(?))";
    
    public static final String SQL_AUTH_USER =
    "select count(*) as user_count from user where email = ? and password = sha1(?)";
    
    public static final String SQL_GET_USER =
    "select * from user where email = ? and password = sha1(?)";
}
