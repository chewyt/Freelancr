drop database if exists freelancr;
create database freelancr;
use freelancr;
 
 create table user(
    user_id varchar(64) not null,
    name varchar(64) not null,
    username varchar(16) not null,
    password varchar(512) not null,
    email varchar(64) not null,
    bio text,
    location varchar(64) not null,
    type enum('freelancer','client') not null,
    auth_token varchar(64) not null,
    
    primary key (user_id)
 );
 
 drop table if exists projects;
 
 create table projects(
 
	project_id varchar(64) not null,
    project_title varchar(64) not null,
    project_brief text not null,
    project_specialties varchar(256) not null,
    project_budget_range enum('b_100to1K','b_1Kto10K','b_10Kto20K','b_20Kto50K','b_over50K') not null,

    project_status bool not null,
    project_posted_date datetime not null,
    project_display bool not null,
    
    project_client_id varchar(64) not null,
    project_freelancer_id varchar(64),
    project_smart_contract_id varchar(64),
    project_cost int,
  
    project_end_date date,
    project_rating int,
    project_reviews text,
    
    primary key(project_id),
    constraint fk_project_client_id
		foreign key(project_client_id)
        references user(user_id)
 
 );
 
 create table finterest(
 
	finterest_id varchar(64) not null,
    finterest_project_id varchar(64) not null,
    finterest_freelancer_id varchar(64) not null,
    finterest_comments text not null,
    finterest_budget int not null,
    finterest_status bool not null,
    
    primary key(finterest_id),
    constraint fk_project_id
		foreign key(finterest_project_id)
        references projects(project_id),
	constraint fk_freelancer_id
		foreign key(finterest_freelancer_id)
        references user(user_id)
 
 );

 drop table if exists contracts;

create table contracts(
 
	project_smart_contract_id varchar(64) not null,
    freelancer_id varchar(64) not null,
    client_id varchar(64) not null,
    project_id varchar(64) not null,
    project_cost int not null,
    project_status varchar(16) not null,
    
    primary key(project_smart_contract_id),
    constraint fk_contract_project_id
		foreign key(project_id)
        references projects(project_id),
	constraint fk_contract_freelancer_id
		foreign key(freelancer_id)
        references user(user_id),
	constraint fk_contract_client_id
		foreign key(client_id)
        references user(user_id)
 
 );

describe contracts;
 