drop database if exists freelancr;
create database freelancr;
use freelancr;

-- Create a table called tasks

create table task(

    tid int auto_increment not null,
    username varchar(64) not null,
    task_name varchar(256) not null,
    priority enum('low', 'med','high') default 'low',
    due_date date,
    primary key(tid)

);

-- tid -auto increment ,primary key
-- username - 64 chars
-- task_name - 256 chars
-- priority - low , med , high
-- due_date - date


-- create user 
create table user(
    user_id varchar(64) not null,
    name varchar(64) not null,
    username varchar(16) not null,
    password varchar(512) not null,
    email varchar(64) not null,
    bio text,
    location varchar(64) not null,
    type enum('freelancer','client') not null,
    
    -- specializations: Specialties
    -- experience: number //in years
    -- education: string
    -- avg_rating: number //float type
    -- completed_projects: number
    -- in_progress_projects: number
    primary key (user_id)
 );


 create table userlist(
    username varchar(64) not null,
    password varchar(512) not null,
    primary key (email)
 );