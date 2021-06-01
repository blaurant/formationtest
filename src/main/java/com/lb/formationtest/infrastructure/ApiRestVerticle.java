package com.lb.formationtest.infrastructure;

import DDD.framework.Strings;
import com.lb.formationtest.application.ApplicationException;
import com.lb.formationtest.application.TransactionService;
import com.lb.formationtest.domain.Actor;
import com.lb.formationtest.domain.WESC;
import com.lb.formationtest.infrastructure.repositories.InMemoryTransactionRepo;
import com.lb.formationtest.infrastructure.repositories.StaticWESCRepo;
import com.lb.formationtest.infrastructure.restapi.adaptor.ApiAdaptor;
import com.lb.formationtest.infrastructure.restapi.dto.*;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.HashSet;
import java.util.Set;

import static com.lb.formationtest.infrastructure.restapi.adaptor.Codec.fromJson;
import static com.lb.formationtest.infrastructure.restapi.adaptor.Codec.toJson;


public class ApiRestVerticle extends AbstractVerticle {

    private int port;

    public static final String TRANSAC_ID = "id";
    public static final String WESC_ID = "wescId";

    public static final String PREFIX = "/api/v1";
    public static final String TRANSACTION = "/transaction";
    public static final String CHAIN = "/chain";
    public static final String QUALITY = "/quality";
    public static final String WESC = "/WESC";
    public static final String CARRIER = "/carrier";
    public static final String RECYCLER = "/recycler";
    public static final String CLEARALL = "/clearall";
    public static final String REMOVAL_REQUEST = "/removalRequest";
    public static final String TRANSPORT = "/transport";
    public static final String RECEPTION = "/reception";
    public static final String BAF = "/baf";
    public static final String VALIDATION_BYOPE = "/validationByOpe";
    public static final String VALIDATION_BYCARRIER = "/validationByCarrier";
    public static final String UPLOAD = "/upload";

    private TransactionService transactionService = new TransactionService(new InMemoryTransactionRepo(), new StaticWESCRepo());
    private ApiAdaptor apiAdaptor = new ApiAdaptor(transactionService);

    public ApiRestVerticle() {
        this(80);
    }

    public ApiRestVerticle(int port) {
        super();
        this.port = port;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        //CORSHandler(router);
        bodyHandler(router);
        routesHandlers(router);
        vertx.createHttpServer()
                .requestHandler(router)
                .listen(port);
    }

    //region router config
    private void CORSHandler(Router router) {
        //  Enables CORS for all methods and headers
        //TODO intended for dev POC purposes only - not for production use
        Set<String> allowedHeaders = new HashSet<>();
        allowedHeaders.add("Access-Control-Allow-Origin");
        //allowedHeaders.add("Access-Control-Allow-Credentials");
        allowedHeaders.add("Access-Control-Allow-Headers");
        allowedHeaders.add("Access-Control-Allow-Methods");
        allowedHeaders.add("Access-Control-Expose-Headers");
        allowedHeaders.add("Access-Control-Request-Method");
        allowedHeaders.add("Access-Control-Request-Headers");
        //allowedHeaders.add("Access-Control-Max-Age");
        allowedHeaders.add("Origin");
        router.route().handler(CorsHandler
                .create("*")
                .allowCredentials(false)
                .maxAgeSeconds(-1)
                .allowedMethods(Set.of(HttpMethod.values()))
                .allowedHeaders(allowedHeaders));
    }

    private void bodyHandler(Router router) {
        router.route().handler(BodyHandler.create());
    }

    private void routesHandlers(Router router) {
        router.get(PREFIX + WESC + "/:" + WESC_ID).handler(this::getWesc);
        router.get(PREFIX + CARRIER).handler(this::getALlCarrier);
        router.get(PREFIX + RECYCLER).handler(this::getALlRecycler);
        router.get(PREFIX + QUALITY).handler(this::getQuality);
        router.get(PREFIX + CHAIN).handler(this::getChainByWESC);
        router.post(PREFIX + REMOVAL_REQUEST).handler(this::postRemovalRequest);
        router.get(PREFIX + TRANSACTION + "/:" + TRANSAC_ID).handler(this::getTransaction);
        router.post(PREFIX + TRANSACTION + CLEARALL).handler(this::clearAllTransaction);
        router.get(PREFIX + TRANSACTION).handler(this::getTransactionByWesc);
        router.post(PREFIX + TRANSPORT).handler(this::postTransport);
        router.post(PREFIX + RECEPTION).handler(this::postReception);
        router.post(PREFIX + TRANSACTION + "/:" + TRANSAC_ID + VALIDATION_BYOPE).handler(this::postOpeValidation);
        router.post(PREFIX + TRANSACTION + "/:" + TRANSAC_ID + VALIDATION_BYCARRIER).handler(this::postCarrierValidation);
        router.post(PREFIX + TRANSACTION + "/:" + TRANSAC_ID + UPLOAD).handler(this::postUpload);
        router.get(PREFIX + TRANSACTION + "/:" + TRANSAC_ID + BAF).handler(this::getBAFForTransaction);
        router.post(PREFIX + BAF).handler(this::postBAFForCarrier);
    }
    //endregion

    //region REST API
    private void postRemovalRequest(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respCreatedWithBody(ctx, apiAdaptor.POSTRemovalRequest(body2Dto(ctx, RemovalRequest.class))));
    }

    private void postOpeValidation(RoutingContext ctx) {
        postValidation(ctx, Actor.OPE);
    }

    private void postCarrierValidation(RoutingContext ctx) {
        postValidation(ctx, Actor.CARRIER);
    }

    private void postValidation(RoutingContext ctx, Actor actor) {
        respWithSimpleErrMgt(ctx, () ->
                respCreatedWithBody(ctx, apiAdaptor.POSTValidation(transacIdFromUrl(ctx), actor)));
    }

    private void postUpload(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respCreatedWithBody(ctx, apiAdaptor.POSTUpload(transacIdFromUrl(ctx), body2Dto(ctx, UploadDoc.class))));
    }

    private void postTransport(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respCreatedWithBody(ctx, apiAdaptor.POSTTransport(body2Dto(ctx, Transport.class))));
    }

    private void postReception(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respCreatedWithBody(ctx, apiAdaptor.POSTReception(body2Dto(ctx, Reception.class))));
    }

    private void getBAFForTransaction(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETBAFItemForTransaction(transacIdFromUrl(ctx))));
    }

    private void postBAFForCarrier(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.POSTBAFForCarrier(body2Dto(ctx, BafRequest.class))));
    }

    private void clearAllTransaction(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () -> {
            apiAdaptor.DELETEAllTransaction();
            respOk(ctx);
        });
    }

    private void getALlCarrier(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETAllCarrier()));
    }

    private void getALlRecycler(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETALLRecycler()));
    }

    private void getQuality(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETAllQuality()));
    }

    private void getChainByWESC(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETChainByWESC(wescIdFromUrl(ctx))));
    }

    private void getTransaction(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETTransaction(transacIdFromUrl(ctx))));
    }

    private void getWesc(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETWesc(wescIdFromUrl(ctx))));
    }

    private void getTransactionByWesc(RoutingContext ctx) {
        respWithSimpleErrMgt(ctx, () ->
                respOkWithBody(ctx, apiAdaptor.GETTransactionByWesc(wescIdFromUrl(ctx))));
    }
    //endregion

    //region data extractors
    private String transacIdFromUrl(RoutingContext ctx) {
        return ctx.request().getParam(TRANSAC_ID);
    }

    private WESC.Id wescIdFromUrl(RoutingContext ctx) {
        return com.lb.formationtest.domain.WESC.Id.fromString(ctx.request().getParam(WESC_ID));
    }

    private static String extractBody(RoutingContext ctx) {
        return Strings.requireNotEmpty(ctx.getBodyAsString(), "There is No Body in the POST");
    }
    //endregion

    //region response
    private static <T> T body2Dto(RoutingContext ctx, Class<T> clazz) {
        return fromJson(extractBody(ctx), clazz);
    }

    private void respWithSimpleErrMgt(RoutingContext ctx, Runnable restProcess) {
        try {
            restProcess.run();
        } catch (ApplicationException ex) {
            sendError(404, ex.getMessage(), ctx.response());
        } catch (Throwable ex) {
            ex.printStackTrace();
            sendError(400, ex.getMessage(), ctx.response());
        }
    }

    private void sendError(int statusCode, String msg, HttpServerResponse response) {
        response.setStatusCode(statusCode)
                .putHeader("content-type", "text/plain")
                .end(msg);
    }

    private void respOk(RoutingContext ctx) {
        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end();
    }

    private void respOkWithBody(RoutingContext ctx, Object dto) {
        ctx.response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json")
                .end(toJson(dto));
    }

    private void respCreatedWithBody(RoutingContext ctx, Object dto) {
        ctx.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json")
                .end(toJson(dto));
    }
    //endregions
}

