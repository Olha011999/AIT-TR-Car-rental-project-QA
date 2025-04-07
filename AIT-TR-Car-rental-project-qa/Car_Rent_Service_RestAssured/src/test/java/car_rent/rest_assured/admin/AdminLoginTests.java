package car_rent.rest_assured.admin;


import car_rent.rest_assured.dto.ErrorResponseDto;
import car_rent.rest_assured.dto.LoginRequestDto;
import car_rent.rest_assured.dto.TokenResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AdminLoginTests {
    private static final  String LOGIN_URL = "https://car-rental-cymg8.ondigitalocean.app/api/auth/login";
    private static final Gson GSON = new Gson();
    SoftAssert softAssert = new SoftAssert();


    @Test
    public void SuccessfulAdministratorAuthorization() throws IOException {
        //Body
        String jsonBody = """
                {
                  "email": "admin_1@car-rent.de",
                  "password": "Admin-Pass#007"
                }""";

        //Request
        Response response = Request.Post(LOGIN_URL)
                .bodyString(jsonBody, ContentType.APPLICATION_JSON)
                .execute();

        System.out.println(response);

        //answer
        String responseJson = response.returnContent().asString();
        System.out.println(responseJson);

        JsonElement jsonElement = JsonParser.parseString(responseJson);
        JsonElement token = jsonElement.getAsJsonObject().get("token");

        System.out.println(token);

        Assert.assertNotNull(token);
    }

    @Test
    public void SuccessfulAdministratorAuthorizationWithDto() throws IOException {
        LoginRequestDto loginRequestDtoAdmin = LoginRequestDto.builder()
                .email("admin_1@car-rent.de")
                .password("Admin-Pass#007")
                .build();

        Response responseLoginAdmin = Request.Post(LOGIN_URL)
                .bodyString(GSON.toJson(loginRequestDtoAdmin), ContentType.APPLICATION_JSON)
                .execute();
        System.out.println(responseLoginAdmin);
        String stringAdminJson = responseLoginAdmin.returnContent().asString();
        System.out.println(stringAdminJson);

        TokenResponseDto tokenResponseDto = GSON.fromJson(stringAdminJson, TokenResponseDto.class);
        System.out.println(tokenResponseDto);
        String accessToken = tokenResponseDto.getAccessToken();
        System.out.println(accessToken);
        String refreshToken = tokenResponseDto.getRefreshToken();
        System.out.println(refreshToken);
    }

    @Test
    public void  IncorrectAdminPasswordWithDto() throws IOException {
        LoginRequestDto wrongAdminPasswordRequestDto = LoginRequestDto.builder()
                .email("admin_1@car-rent.de")
                .password("admin-Pass#008")
                .build();

        Response wrongAdminPassword = Request.Post(LOGIN_URL)
                .bodyString(GSON.toJson(wrongAdminPasswordRequestDto), ContentType.APPLICATION_JSON)
                .execute();

        HttpResponse httpResponseWrongAdminPassword = wrongAdminPassword.returnResponse();
        System.out.println("Raw answer: " + httpResponseWrongAdminPassword);

        //извлекаем тело ответа
        InputStream contentWrongAdminPassword = httpResponseWrongAdminPassword.getEntity().getContent();

        //считываем
        BufferedReader bufferedReaderWrongAdminPassword = new BufferedReader(new InputStreamReader(contentWrongAdminPassword));

        StringBuilder sbWrongAdminPassword = new StringBuilder();
        String lineWrongAdminPassword;

        while ((lineWrongAdminPassword = bufferedReaderWrongAdminPassword.readLine()) != null) {
            sbWrongAdminPassword.append(lineWrongAdminPassword);
            System.out.println(lineWrongAdminPassword);
        }

        ErrorResponseDto errorResponseDtoAdmin = GSON.fromJson(sbWrongAdminPassword.toString(), ErrorResponseDto.class);
        System.out.println(errorResponseDtoAdmin);
        softAssert.assertEquals(errorResponseDtoAdmin.getMessage(), "Password or email incorrect");
        softAssert.assertAll();
    }
}
