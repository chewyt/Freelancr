# Freelancr
Final Project for NUSISS


SETUP steps

>>For Database:

Create a database cluster in Digital Ocean
Connect to MySQL WorkBench to create schema and tables using schema.sql
Create a user with control access to freelancr database
Get the connection string of the specified user and add it into SPRING_DATASOURCE_URL of heroku ConfigVARS later


>>PAAS side:

Create a heroku app

Configurations required on heroku:

1. SPRING_DATASOURCE_URL (which includes the credentials within a connection string)
2. APIKEY_PRIVATE

Following CLI commands needed
`
git init
`
`
git commit
`
`
git remote add heroku [heroku git url]
`

`
git push heroku main
`
`
heroku open
`
