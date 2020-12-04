package com.parkinson.ben.gameofthree.testing.contract;

import com.parkinson.ben.gameofthree.model.GameMove;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

// note that for now this depends on both player servers running
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestContext.class
})
public class GameControllerIT {

    @Autowired
    private URI serverUri;

    @Test
    public void testValidMoveIsAccepted() {
        GameMove gameMove = new GameMove(9, 0);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/gamemoves")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testInvalidMoveRespondsWithBadRequest() {
        GameMove gameMove = new GameMove(9, 5);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/gamemoves")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testZeroRespondsWithError() {
        GameMove gameMove = new GameMove(0);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/gamemoves")
                .then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

}
