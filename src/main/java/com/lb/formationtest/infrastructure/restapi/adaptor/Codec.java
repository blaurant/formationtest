package com.lb.formationtest.infrastructure.restapi.adaptor;

import com.lb.formationtest.domain.Carrier;
import com.lb.formationtest.domain.Quality;
import com.lb.formationtest.domain.Recycler;
import com.lb.formationtest.domain.StockPile;
import com.lb.formationtest.domain.*;
import com.lb.formationtest.infrastructure.restapi.dto.Chain;
import com.lb.formationtest.infrastructure.restapi.dto.Document;
import com.lb.formationtest.infrastructure.restapi.dto.Transaction;
import com.lb.formationtest.infrastructure.restapi.dto.WESC;
import com.lb.formationtest.infrastructure.restapi.dto.*;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Codec {

    public static final String DATETIME_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    public static final JsonSerializer<LocalDateTime> localDateTimeSer = (dateTime, type, jsonSerializationContext) -> dateTime == null ? null : new JsonPrimitive(formatter.format(dateTime));
    public static final JsonDeserializer<LocalDateTime> localDateTimeDeser = (jsonElement, type, jsonDeserializationContext) -> jsonElement == null ? null : LocalDateTime.parse(jsonElement.getAsString(), formatter);

    static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, localDateTimeSer)
            .registerTypeAdapter(LocalDateTime.class, localDateTimeDeser)
            .setPrettyPrinting()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }

    public static final Function<Quality, com.lb.formationtest.infrastructure.restapi.dto.Quality> toQualityDto = quality -> new com.lb.formationtest.infrastructure.restapi.dto.Quality(
            quality.name(),
            quality.code(),
            quality.wording());

    public static final List<com.lb.formationtest.infrastructure.restapi.dto.Quality> toQualityListDto() {
        return Stream.of(Quality.values())
                .map(toQualityDto)
                .collect(toList());
    }

    public static final Function<com.lb.formationtest.domain.Chain, Chain> toChainDto = chain -> new Chain(
            chain.wescId.asString(),
            chain.quality.name(),
            chain.carrierId.asString(),
            chain.recyclerId.asString());

    public static final Function<Chains, List<com.lb.formationtest.infrastructure.restapi.dto.Chain>> toChainListDto = chains -> chains.stream()
            .map(toChainDto)
            .collect(toList());

    private static final Function<Carrier, com.lb.formationtest.infrastructure.restapi.dto.Carrier> toCarrierDto = carrier -> new com.lb.formationtest.infrastructure.restapi.dto.Carrier(
            carrier.getId().asString(),
            carrier.name.asString());

    public static final Function<Carriers, List<com.lb.formationtest.infrastructure.restapi.dto.Carrier>> toCarrierListDto = carriers -> carriers.stream()
            .map(toCarrierDto)
            .collect(toList());

    private static final Function<Recycler, com.lb.formationtest.infrastructure.restapi.dto.Recycler> toRecyclerDto = recycler -> new com.lb.formationtest.infrastructure.restapi.dto.Recycler(
            recycler.getId().asString(),
            recycler.name.asString());

    public static final Function<Recyclers, List<com.lb.formationtest.infrastructure.restapi.dto.Recycler>> toRecyclerListDto = recyclers -> recyclers.stream()
            .map(toRecyclerDto)
            .collect(toList());

    public static final Function<StockPile, com.lb.formationtest.infrastructure.restapi.dto.StockPile> toStockPileDto = stockPile -> new com.lb.formationtest.infrastructure.restapi.dto.StockPile(
            stockPile.quality.name(),
            stockPile.quantity.asStringWith3digit());

    public static final Function<StockPiles, List<com.lb.formationtest.infrastructure.restapi.dto.StockPile>> toDtockPilesList = stockPiles -> stockPiles.stream()
            .map(toStockPileDto)
            .collect(toList());

    public static final Function<com.lb.formationtest.domain.WESC, WESC> toWESCDto = wesc -> new WESC(
            wesc.getId().asString(),
            wesc.name.asString(),
            toDtockPilesList.apply(wesc.stock.getStockPiles()));

    public static final Function<com.lb.formationtest.domain.Document, Document> toDocument = document -> new Document(document.value);

    public static final Function<com.lb.formationtest.domain.Transaction, Transaction> toTransactionDto = transac -> new Transaction(
            transac.getId().asString(),
            transac.transacRef.asString(),
            transac.currentState().name(),
            transac.removalRequestRef.asString(),
            formatter.format(transac.creationDate),
            transac.quality.name(),
            transac.quantity.asStringWith3digit(),
            formatter.format(transac.availableDate),
            toChainDto.apply(transac.chain),
            transac.wescId.asString(),
            transac.getTransportRef() == null ? null : transac.getTransportRef().asString(),
            transac.getCollectDate() == null ? null : formatter.format(transac.getCollectDate()),
            transac.getDeliveryDate() == null ? null : formatter.format(transac.getDeliveryDate()),
            transac.getReceptionRef() == null ? null : transac.getReceptionRef().asString(),
            transac.getReceptionDate() == null ? null : formatter.format(transac.getReceptionDate()),
            transac.getReceptionQuality() == null ? null : transac.getReceptionQuality().name(),
            transac.getReceptionQuantity() == null ? null : transac.getReceptionQuantity().asStringWith3digit(),
            transac.getDocuments().isEmpty() ? null : transac.getDocuments().stream().map(toDocument).collect(toList())
    );

    public static final Function<com.lb.formationtest.domain.Transactions, List<Transaction>> toTransactionListDto = transactions -> transactions.stream()
            .map(toTransactionDto)
            .collect(toList());

    public static final Function<com.lb.formationtest.domain.billing.BafItem, BafItem> toBafItemDto = bafItem -> new BafItem(
            toTransactionDto.apply(bafItem.transaction),
            bafItem.transaction.calculateTransportPrice().truncateAtCents().asString(),
            formatter.format(bafItem.transaction.getCollectDate()),
            formatter.format(bafItem.transaction.getDeliveryDate()),
            bafItem.transaction.getTranspotIndex().asString());

    public static final Function<com.lb.formationtest.domain.billing.Baf, Baf> toBafItemListDto = baf -> new Baf(
            baf.isEmpty() ? null : baf.bafItems.stream().map(toBafItemDto).collect(toList()),
            baf.isEmpty() ? null : baf.RefIndex.asString(),
            baf.isEmpty() ? null : baf.price().truncateAtCents().asString());
}
