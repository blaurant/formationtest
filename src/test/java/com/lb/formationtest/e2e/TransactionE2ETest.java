package com.lb.formationtest.e2e;

import com.lb.formationtest.domain.Reference;
import com.lb.formationtest.infrastructure.ApiRestVerticle;
import io.vertx.core.Vertx;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionE2ETest {

    String SERVER = "localhost";
    int PORT = 9191;

    final Vertx vertx = Vertx.vertx();

    @Before
    public void setUp() throws Exception {
        vertx.deployVerticle(new ApiRestVerticle(PORT));
        Reference.clearAll();
    }

    @Test
    public void TransactionNominalPath() {

        // removalRequest
        HttpResponse<String> response = Unirest
                .post("http://" + SERVER + ":" + PORT + "/api/v1/removalRequest")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .header("Content-Type", "application/json")
                .body("{\n    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n    \"quality\": \"PS\",\n    \"quantity\": \"12.200\",\n    \"availableDate\": \"2012-04-23\",\n    \"chain\": {\n        \"wescId\": \"4f398ca5-5f55-4671-b148-61ca73d5a70c\",\n        \"quality\": \"PS\",\n        \"carrierId\": \"bb9c3fd9-d7f7-4baa-b34a-3fc00fbe40de\",\n        \"recyclerId\": \"fbcb4215-aa9c-41a5-aef0-53eec639c92f\"\n    },\n    \"wescId\": \"4f398ca5-5f55-4671-b148-61ca73d5a70c\"\n}")
                .asString();
        System.out.println(response.getBody());

        // validationByOpe
        response = Unirest
                .post("http://" + SERVER + ":" + PORT + "/api/v1/transaction/b41fd3da-28b3-11eb-adc1-0242ac120002/validationByOpe")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .header("Content-Type", "application/json")
                .body("{\n    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n    \"collectDate\": \"2012-05-01\",\n    \"deliveryDate\": \"2012-05-02\"\n}")
                .asString();
        System.out.println(response.getBody());

        // validationByCarrier
        response = Unirest.post("http://" + SERVER + ":" + PORT + "/api/v1/transaction/b41fd3da-28b3-11eb-adc1-0242ac120002/validationByCarrier")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .header("Content-Type", "application/json")
                .body("{\n    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n    \"collectDate\": \"2012-05-01\",\n    \"deliveryDate\": \"2012-05-02\"\n}")
                .asString();
        System.out.println(response.getBody());

        // transport
        response = Unirest.post("http://" + SERVER + ":" + PORT + "/api/v1/transport")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .header("Content-Type", "application/json")
                .body("{\n    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n    \"collectDate\": \"2012-05-01\",\n    \"deliveryDate\": \"2012-05-01\"\n}")
                .asString();
        System.out.println(response.getBody());

        // validationByOpe
        response = Unirest.post("http://" + SERVER + ":" + PORT + "/api/v1/transaction/b41fd3da-28b3-11eb-adc1-0242ac120002/validationByOpe")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .body("")
                .asString();
        System.out.println(response.getBody());

        // reception
        response = Unirest.post("http://" + SERVER + ":" + PORT + "/api/v1/reception")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .header("Content-Type", "application/json")
                .body("{\n    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n    \"receptionDate\": \"2012-05-01\",\n    \"receptionQuality\": \"PS\",\n    \"receptionQuantity\": \"12.200\"\n}")
                .asString();
        System.out.println(response.getBody());

        // validationByOpe
        response = Unirest.post("http://" + SERVER + ":" + PORT + "/api/v1/transaction/b41fd3da-28b3-11eb-adc1-0242ac120002/validationByOpe")
                .header("X-Auth-Token", "62544189d0a26ad692b3ea1c8e04ba28")
                .body("")
                .asString();
        System.out.println(response.getBody());

        // baf
        response = Unirest
                .get("http://" + SERVER + ":" + PORT + "/api/v1/transaction/b41fd3da-28b3-11eb-adc1-0242ac120002/baf")
                .asString();
        System.out.println(response.getBody());

        String espectedEnd = "{\n" +
                "  \"transaction\": {\n" +
                "    \"transactionId\": \"b41fd3da-28b3-11eb-adc1-0242ac120002\",\n" +
                "    \"transactionRef\": \"C1\",\n" +
                "    \"state\": \"OPE_RECEPTION_CONFIRMED\",\n" +
                "    \"removalRequestRef\": \"DE1\",\n" +
                "    \"creationDate\": \"" + now() + "\",\n" +
                "    \"quality\": \"PS\",\n" +
                "    \"quantity\": \"12,200\",\n" +
                "    \"availableDate\": \"2012-04-23\",\n" +
                "    \"chain\": {\n" +
                "      \"wescId\": \"4f398ca5-5f55-4671-b148-61ca73d5a70c\",\n" +
                "      \"quality\": \"PS\",\n" +
                "      \"carrierId\": \"bb9c3fd9-d7f7-4baa-b34a-3fc00fbe40de\",\n" +
                "      \"recyclerId\": \"fbcb4215-aa9c-41a5-aef0-53eec639c92f\"\n" +
                "    },\n" +
                "    \"wescId\": \"4f398ca5-5f55-4671-b148-61ca73d5a70c\",\n" +
                "    \"transportRef\": \"T1\",\n" +
                "    \"collectDate\": \"2012-05-01\",\n" +
                "    \"deliveryDate\": \"2012-05-01\",\n" +
                "    \"receptionRef\": \"R1\",\n" +
                "    \"receptionDate\": \"2012-05-01\",\n" +
                "    \"receptionQuality\": \"PS\",\n" +
                "    \"receptionQuantity\": \"12,200\"\n" +
                "  },\n" +
                "  \"price\": \"142.70\",\n" +
                "  \"collectDate\": \"2012-05-01\",\n" +
                "  \"deliveryDate\": \"2012-05-01\",\n" +
                "  \"indexOfMonth\": \"178\"\n" +
                "}";

        assertThat(response.getBody()).isEqualTo(espectedEnd);
    }

    @After
    public void tearDown() {
        vertx.close();
    }
}
