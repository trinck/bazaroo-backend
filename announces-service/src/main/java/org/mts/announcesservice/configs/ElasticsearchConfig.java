package org.mts.announcesservice.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.documents.SavedSearchDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

@Configuration
@Slf4j
public class ElasticsearchConfig {


    private final ElasticsearchOperations template;

    public ElasticsearchConfig(ElasticsearchOperations template) {
        this.template = template;
    }

    @PostConstruct
    public void createIndex(){

        log.info("Creating Index ====");
        IndexOperations indexOperations = this.template.indexOps(AnnounceDocument.class);
        if (!indexOperations.exists()) {
            log.info("Creating Index AnnounceDocument");
            indexOperations.create();
            indexOperations.putMapping(indexOperations.createMapping());
        }

        IndexOperations savedDocIndex = this.template.indexOps(SavedSearchDocument.class);
        if (!savedDocIndex.exists()) {
            log.info("Creating Index SavedSearchDocument");
            savedDocIndex.create();
            savedDocIndex.putMapping(savedDocIndex.createMapping());
        }

        log.info("End of Index Creating ===");
    }
}
