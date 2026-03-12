package com.qa.autotests.api;

import com.qa.autotests.config.TestConfig;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("API Tests")
public class SampleApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = TestConfig.getApiBaseUrl();
    }

    @Test
    @Story("GET request")
    @Description("Verify GET /posts returns a list of posts with status 200")
    public void testGetPostsReturns200() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("size()", greaterThan(0));
    }

    @Test
    @Story("GET request by ID")
    @Description("Verify GET /posts/1 returns the correct post")
    public void testGetPostByIdReturns200() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("title", notNullValue())
            .body("body", notNullValue());
    }

    @Test
    @Story("POST request")
    @Description("Verify POST /posts creates a new post and returns 201")
    public void testCreatePostReturns201() {
        String requestBody = "{ \"title\": \"Test Post\", \"body\": \"Test body content\", \"userId\": 1 }";

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("title", equalTo("Test Post"))
            .body("id", notNullValue())
            .extract()
            .response();

        int createdId = response.jsonPath().getInt("id");
        Assert.assertTrue(createdId > 0, "Created post ID should be positive");
    }

    @Test
    @Story("GET users")
    @Description("Verify GET /users returns a list of users")
    public void testGetUsersReturns200() {
        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/users")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].id", notNullValue())
            .body("[0].name", notNullValue())
            .body("[0].email", notNullValue());
    }
}
