import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class AuthTest {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost:9999")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void createUser(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @BeforeAll
    public static void setUpActive() {
        RegistrationDto vasya = new RegistrationDto("vasya", "password", "active");
        RegistrationDto ivan = new RegistrationDto("ivan", "555555", "blocked");

        createUser(vasya);
        createUser(ivan);
    }


    @Test
    public void withStatusActive() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "password"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200);
    }

    @Test
    public void withStatusBlocked() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("ivan", "555555"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    public void withInvalidLogin() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("anton", "password"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    public void withInvalidPassword() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", "123456"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    public void withWithLoginNull() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto(null, "password"))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    public void withWithPasswordNull() {
        given()
                .spec(requestSpec)
                .body(new RegistrationDto("vasya", null))
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }
}