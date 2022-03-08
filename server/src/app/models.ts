export interface Models {
}

export interface User extends UserSummary{

    bio : string //About myself
    location: string
    profile_id: string //used to link to cdn to show static pictures
    type: string // either client or freelancer

}

export interface UserSummary {

    user_id: string // auto uuid
    name: string  
    username: string  // can be changed and duplicated
    password: string
    email: string    // unique to register in system
    auth_token: string //to be stored in sessionStorage
}

export interface Client extends User {

    

}

export interface FreeLancer extends User {

    specializations: Specialties
    experience: number //in years
    education: string
    avg_rating: number //float type
    completed_projects: number
    in_progress_projects: number

    // option: workhistory

}

export interface Project {

    project_id: string // auto uuid and sha1
    project_title: string
    project_brief: string
    project_specialties: string[] //checkbox selection
    project_budget_range: string //enum selection

    project_status: boolean //true for OPEN, false for CLOSED
    project_posted_date : string
    project_display: boolean //if deleted turn to false

    //foreign key
    project_client_id: string
    project_freelancer_id: string
    project_smart_contract_id: string

    //Message owner button press to finalize price
    project_cost: number //type decimal
    // TODO: what variables required to allow owner to hire which freelancer for the job 

    
    //after project completion
    project_end_date: Date
    project_rating: number  // 1 to 5 stars
    project_reviews: string  //temp usage as project_stages

    hover:boolean
    username:string
}

export interface Specialties{
    Animation: boolean
    Branding: boolean
    Illustration: boolean
    Web_Design: boolean
    Mobile_Design: boolean
    UX_Design: boolean
}

export interface Project_Count{
    count: number
}

export interface Finterest{
    finterest_id: string
    finterest_project_id: string
    finterest_freelancer_id: string
    finterest_freelancer_username: string
    finterest_comments: string
    finterest_budget: number
    finterest_status: boolean  // true means accepted
}

export interface Currency{
    amount: number
}

export interface Contract{
    smart_contract_id: string
    username:string //temp variable
    freelancer_id:string
    client_id:string
    project_id:string
    project_cost: number
    status: string  //auto added later in different stages
}