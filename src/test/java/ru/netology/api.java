package ru.netology;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class MobileBankApiTestV1 {

    @Test
    void shouldReturnDemoAccounts() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturnThreeAccounts() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("", hasSize(3));
    }

    @Test
    void shouldReturnFirstAccountData() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].balance", equalTo(992821429))
                .body("[0].currency", equalTo("RUB"));
    }

    @Test
    void shouldReturnSecondAccountWithUSD() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("[1].id", equalTo(2))
                .body("[1].balance", equalTo(2877210))
                .body("[1].currency", equalTo("USD"));
    }

    @Test
    void shouldReturnThirdAccountData() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("[2].id", equalTo(3))
                .body("[2].balance", equalTo(1204044))
                .body("[2].currency", equalTo("RUB"));
    }

    @Test
    void shouldContainBothCurrencies() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("currency", hasItems("RUB", "USD"));
    }

    @Test
    void shouldHaveAllPositiveBalances() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("balance", everyItem(greaterThan(0)));
    }

    @Test
    void shouldReturn404ForInvalidPath() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/invalid/path")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldFailOnWrongCurrency() {
        given()
                .baseUri("http://0.0.0.0:9999")
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("[0].currency", equalTo("USD"));
    }
}