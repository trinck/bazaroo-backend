package org.mts.notificationservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

@Configuration
public class ElasticsearchConfig {
    @Autowired
    private ElasticsearchTemplate template;

//    public void createIndex(){
//        IndexOperations indexOperations = this.template.indexOps(TrackingEventDocument.class);
//        if (!indexOperations.exists()) {
//            indexOperations.create();
//            indexOperations.putMapping(indexOperations.createMapping());
//        }
//    }
}
