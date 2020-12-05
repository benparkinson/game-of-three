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
        GameMove gameMove = new GameMove(18, 0);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/manual/gamemoves")
                .then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testInvalidManualMoveRespondsWithBadRequest() {
        GameMove gameMove = new GameMove(18, 5);
        RestAssured.with()
                .header("Content-Type", "application/json")
                .body(gameMove)
                .when().post(serverUri + "/gameofthree/v1/api/manual/gamemoves")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void testStartGameRespondsWithCreatedAndValidFirstMove() {
        GameMove firstMoveResponse = RestAssured.with()
                .header("Content-Type", "application/json")
                .when().post(serverUri + "/gameofthree/v1/api/games")
                .then().statusCode(HttpStatus.SC_CREATED)
                .extract().as(GameMove.class);

        assertThat(firstMoveResponse.getAddend()).isEqualTo(0);
        assertThat(firstMoveResponse.getResult()).isGreaterThan(0);
    }

}
