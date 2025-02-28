package org.mts.trackingservice.serdes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.mts.trackingservice.documents.TrackingEventDocument;

import java.util.List;

public class ListTrackingEventDocumentSerde implements Serde<List<TrackingEventDocument>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    /**
     * @return
     */
    @Override
    public Serializer<List<TrackingEventDocument>> serializer() {
        return (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (Exception e) {
                throw new RuntimeException("Serialization error", e);
            }
        };
    }

    /**
     * @return
     */
    @Override
    public Deserializer<List<TrackingEventDocument>> deserializer() {
        return (topic, data) -> {
            try {
                return objectMapper.readValue(data, new TypeReference<>() {});
            } catch (Exception e) {
                throw new RuntimeException("Deserialization error", e);
            }
        };
    }
}
