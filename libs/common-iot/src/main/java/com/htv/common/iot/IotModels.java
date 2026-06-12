package com.htv.common.iot;

import java.time.Instant;

public final class IotModels {
    private IotModels() {
    }

    public enum SensorType {TEMPERATURE, HUMIDITY, PRESSURE, LIGHT, MOTION}

    public record Device(String deviceId, String name, String location, String status) {
    }

    public record TelemetryPayload(String deviceId, SensorType sensorType, double value, String unit,
                                   Instant timestamp) {
    }

    public record MqttTopic(String tenant, String deviceId, String channel) {
        public String value() {
            return "tenant/%s/devices/%s/%s".formatted(tenant, deviceId, channel);
        }
    }
}
