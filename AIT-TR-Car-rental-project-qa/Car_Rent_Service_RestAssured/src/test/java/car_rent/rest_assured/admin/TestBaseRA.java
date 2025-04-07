package car_rent.rest_assured.admin;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBaseRA {

    public final String authLoginDto = "/auth/login";
    @BeforeMethod
    public void init(){
        RestAssured.baseURI = "https://car-rental-cymg8.ondigitalocean.app/";
        RestAssured.basePath = "api";


    }
}
