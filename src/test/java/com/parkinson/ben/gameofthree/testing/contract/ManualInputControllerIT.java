package com.parkinson.ben.gameofthree.testing.contract;

import com.parkinson.ben.gameofthree.model.GameMove;
import com.parkinson.ben.gameofthree.model.ManualGameMove;
import com.parkinson.ben.gameofthree.model.PlayMode;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

// note that for now this depends on both player servers running
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestContext.class
})
public class ManualInputControllerIT {

    @Autowired
    private URI serverUri;

    @Test
    public void testManualValidMoveIsAccepted() {
        ManualGameMove gameMove = new ManualGameMove(18, 0);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/manual/gamemoves")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testInvalidManualMoveRespondsWithBadRequest() {
        ManualGameMove gameMove = new ManualGameMove(18, 5);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/manual/gamemoves")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testStartGameRespondsWithCreated() {
        RestAssured.with()
                .header("Content-Type", "application/json")
                .when().post(serverUri + "/gameofthree/v1/api/games")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testGetPlayMode() {
        RestAssured.with()
                .when().get(serverUri + "/gameofthree/v1/api/playmode")
                .then().statusCode(HttpStatus.SC_OK)
                .extract().as(PlayMode.class);
    }
}
