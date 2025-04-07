package car_rent.rest_assured.admin.authentication;

import car_rent.rest_assured.admin.TestBaseRA;
import car_rent.rest_assured.dto.ErrorResponseDto;
import car_rent.rest_assured.dto.LoginRequestDto;
import car_rent.rest_assured.dto.TokenResponseDto;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;


public class AdminAuthRestAssuredTestRA extends TestBaseRA {
    SoftAssert softAssert = new SoftAssert();

    LoginRequestDto loginAdminBody = LoginRequestDto.builder()
            .email("admin@gmail.com")
            .password("Yyyyyyy12345!")
            .build();

    LoginRequestDto incorrectAdminPasswordBody = LoginRequestDto.builder()
            .email("admin@gmail.com")
            .password("Yyyyyyy1234")
            .build();

    LoginRequestDto incorrectAdminEmaildBody = LoginRequestDto.builder()
            .email("admi@gmail.com")
            .password("Yyyyyyy12345!")
            .build();

    LoginRequestDto emptyAdminEmaildBody = LoginRequestDto.builder()
            .email("")
            .password("Yyyyyyy12345!")
            .build();

    LoginRequestDto emptyAdminPasswordBody = LoginRequestDto.builder()
            .email("admin@gmail.com")
            .password("")
            .build();

    @Test
    public void adminSuccessLoginPositiveTest() {
        TokenResponseDto tokenResponseDtoAdmin = given()
                .contentType(ContentType.JSON)
                .body(loginAdminBody)
                .log().all()
                .when()
                .post(authLoginDto)//method
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .extract().response().as(TokenResponseDto.class);//извлекаем токен
        System.out.println("Success Login: " + tokenResponseDtoAdmin);
    }


    @Test
    public void loginIncorrectAdminPasswordNegativeTest() {
        ErrorResponseDto incorrectAdminPassword = given()
                .contentType(ContentType.JSON)
                .body(incorrectAdminPasswordBody)
                .log().all()
                .when()
                .post(authLoginDto)//method
                .then()
                .log().all()
                .assertThat()
                .statusCode(403)
                .extract().response().as(ErrorResponseDto.class);
        System.out.println(incorrectAdminPassword);

        String errorAdminPassword = incorrectAdminPassword.getMessage();
        System.out.println("Incorrect Admin Passwor: " + errorAdminPassword);
        softAssert.assertEquals(errorAdminPassword, "Password or email incorrect");
        softAssert.assertAll();
    }

    @Test
    public void loginIncorrectAdminEmail() {
        ErrorResponseDto incorrectAdminEmail = given()
                .contentType(ContentType.JSON)
                .body(incorrectAdminEmaildBody)
                .log().all()
                .when()
                .post(authLoginDto)//method
                .then()
                .log().all()
                .assertThat()
                .statusCode(403)
                //.statusCode(anyOf(equalTo(403), equalTo(401)))
                .extract().response().as(ErrorResponseDto.class);
        System.out.println(incorrectAdminEmail);

        String errorAdminEmail = incorrectAdminEmail.getMessage();
        System.out.println("Incorrect Admin Email: " + errorAdminEmail);
        softAssert.assertEquals(errorAdminEmail, "Password or email incorrect");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyAdminEmailNegativeTest() {
        ErrorResponseDto emptyAdminEmail = given()
                .contentType(ContentType.JSON)
                .body(emptyAdminEmaildBody)
                .log().all()
                .when()
                .post(authLoginDto)//method
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                //.statusCode(anyOf(equalTo(403), equalTo(401)))
                .extract().response().as(ErrorResponseDto.class);
        System.out.println(emptyAdminEmail);

        String withoutAdminEmail = emptyAdminEmail.getMessage();
        System.out.println("Empty Admin Emai: " + withoutAdminEmail);
        softAssert.assertEquals(withoutAdminEmail, "Validation failed");
        softAssert.assertAll();
    }

    @Test
    public void loginWithEmptyAdminPasswordNegativeTest() {
        ErrorResponseDto emptyAdminPassword = given()
                .contentType(ContentType.JSON)
                .body(emptyAdminPasswordBody)
                .log().all()
                .when()
                .post(authLoginDto)//method
                .then()
                .log().all()
                .assertThat()
                .statusCode(400)
                //.statusCode(anyOf(equalTo(403), equalTo(401)))
                .extract().response().as(ErrorResponseDto.class);
        System.out.println(emptyAdminPassword);

        String withoutAdminPassword = emptyAdminPassword.getMessage();
        System.out.println("Empty Admin Passwor: " + withoutAdminPassword);
        softAssert.assertEquals(withoutAdminPassword, "Validation failed");
        softAssert.assertAll();
    }
}
