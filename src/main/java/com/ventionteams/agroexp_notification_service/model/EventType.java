package com.ventionteams.agroexp_notification_service.model;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    LOT_REJECTION("Lot rejection"),
    LOT_ACCEPTANCE("Lot accepted"),
    LOT_PURCHASED("Lot purchase"),
    LOT_EXPIRED("Lot expiration"),
    BET_OUTBID("Bet outbid");

    private static final Map<String, EventType> cachedEntries = new HashMap<>();

    static {
        for (EventType e : EventType.values()) {
            cachedEntries.put(e.eventName, e);
        }
    }

    private final String eventName;

    EventType(String eventName) {
        this.eventName = eventName;
    }

    public static EventType getByEventName(String eventName) {
        return cachedEntries.get(eventName);
    }
}
