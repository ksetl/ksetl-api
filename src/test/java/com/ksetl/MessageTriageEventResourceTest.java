package com.ksetl;

import com.ksetl.triage.MessageTriageEvent;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class MessageTriageEventResourceTest {

    @Test
    public void getAll() {
        given()
                .when()
                .get("/api/v1/message-triage-events")
                .then()
                .statusCode(200);
    }

    @Test
    public void getById() {
        MessageTriageEvent messageTriageEvent = createMessageTriageEvent();
        MessageTriageEvent saved = given()
                .contentType(ContentType.JSON)
                .body(messageTriageEvent)
                .post("/api/v1/message-triage-events")
                .then()
                .statusCode(201)
                .extract().as(MessageTriageEvent.class);
        MessageTriageEvent got = given()
                .when()
                .get("/api/v1/message-triage-events/{messageTriageEventId}", saved.getMessageTriageEventId())
                .then()
                .statusCode(200)
                .extract().as(MessageTriageEvent.class);
        assertThat(saved).isEqualTo(got);
    }

    @Test
    public void getByIdNotFound() {
        given()
                .when()
                .get("/api/v1/message-triage-events/{messageTriageEventId}", 987654321)
                .then()
                .statusCode(404);
    }

    @Test
    public void post() {
        MessageTriageEvent messageTriageEvent = createMessageTriageEvent();
        MessageTriageEvent saved = given()
                .contentType(ContentType.JSON)
                .body(messageTriageEvent)
                .post("/api/v1/message-triage-events")
                .then()
                .statusCode(201)
                .extract().as(MessageTriageEvent.class);
        assertThat(saved.getMessageTriageEventId()).isNotNull();
    }

    @Test
    public void postFailNoTopic() {
        MessageTriageEvent messageTriageEvent = createMessageTriageEvent();
        messageTriageEvent.setTopic(null);
        given()
                .contentType(ContentType.JSON)
                .body(messageTriageEvent)
                .post("/api/v1/message-triage-events")
                .then()
                .statusCode(400);
    }

    @Test
    public void put() {
        MessageTriageEvent messageTriageEvent = createMessageTriageEvent();
        MessageTriageEvent saved = given()
                .contentType(ContentType.JSON)
                .body(messageTriageEvent)
                .post("/api/v1/message-triage-events")
                .then()
                .statusCode(201)
                .extract().as(MessageTriageEvent.class);
        saved.setErrorMessage("Updated");
        given()
                .contentType(ContentType.JSON)
                .body(saved)
                .put("/api/v1/message-triage-events/{messageTriageEventId}", saved.getMessageTriageEventId())
                .then()
                .statusCode(204);
    }

    private MessageTriageEvent createMessageTriageEvent() {
        MessageTriageEvent messageTriageEvent = new MessageTriageEvent();
        messageTriageEvent.setBootstrapServers("localhost:9092");
        messageTriageEvent.setTopic("test");
        messageTriageEvent.setPartition(1);
        messageTriageEvent.setOffset(1L);
        messageTriageEvent.setErrorMessage("Error");
        return messageTriageEvent;
    }
}