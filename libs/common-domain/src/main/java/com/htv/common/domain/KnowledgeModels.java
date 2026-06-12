package com.htv.common.domain;

import java.time.Instant;
import java.util.Set;

public class KnowledgeModels {
    private KnowledgeModels() {
    }

    public record Topic(String id, String name, String description, Set<String> tags, Instant createdAt) {
    }

    public record Note(String id, String topicId, String title, String mardown, Set<String> tags, Instant createdAt) {
    }

    public record Role(String code, String name) {
    }

    public record Permission(String resource, String action) {
    }

}
