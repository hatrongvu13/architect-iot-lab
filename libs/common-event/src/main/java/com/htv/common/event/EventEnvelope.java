package com.htv.common.event;

import java.time.Instant;
import java.util.Map;

public record EventEnvelope<T>(String eventId, String eventType, String aggregateId, Instant occurredAt,
                               Map<String, String> metadata, T payload) {
}
