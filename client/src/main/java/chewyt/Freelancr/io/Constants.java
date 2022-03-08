package chewyt.Freelancr.io;

public class Constants {
    
    
    //API URLS
    public static final String URL_API = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
    
    

    //Storing in Config VARS of Heroku

    public static final String DATASOURCE_URL = System.getenv("DATASOURCE_URL");
    
    public static final String ENV_APIKEY_PRIVATE = System.getenv("APIKEY_PRIVATE");


}
