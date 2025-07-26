package org.mts.announcesservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.PercolateQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PercolateSearchService implements IPercolateSearchService {
    private final ModelMapper modelMapper;
    private final ElasticsearchClient elasticsearchClient;

    public PercolateSearchService(ModelMapper modelMapper, ElasticsearchClient elasticsearchClient) {
        this.modelMapper = modelMapper;
        this.elasticsearchClient = elasticsearchClient;
    }


    /**
     * @param ad
     * @return
     */
    @Override
    public List<String> findMatchingUsers(AnnounceDocument ad) {


        try {
            PercolateQuery query = new PercolateQuery.Builder()
                    .field("query")
                    .document(JsonData.of(ad))
                    .build();

            // Exécution de la requête
            SearchResponse<JsonData> response = this.elasticsearchClient.search(s -> s
                            .index("saved_searches")
                            .query(q -> q.bool(b -> b
                                    .must(m -> m.percolate(query))
                                    .filter(f -> f.bool(bf -> bf
                                            .must(t -> t.term(tq -> tq.field("type").value(SavedSearchScheduleType.IMMEDIATE.name())))
                                            .must(t -> t.term(tq -> tq.field("active").value(true)))
                                            .mustNot(t->t.term(tq->tq.field("userId").value(ad.getUserId())))
                                    ))
                            )),
                    JsonData.class
            );

            // Extraction des userId depuis les documents correspondants
            return response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(source -> source.to(Map.class).get("userId").toString())
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Gestion de l'exception, par exemple log et retour d'une liste vide
            throw new RuntimeException(e);
        }
    }
}
