package org.mts.announcesservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.clients.StreetClient;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.remote_entities.Media;
import org.mts.announcesservice.remote_entities.Street;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnnounceSearchService implements IAnnounceSearchService{

    private static final Logger logger = LoggerFactory.getLogger(AnnounceSearchService.class);

    private final ElasticsearchClient elasticsearchClient;
    private final StreetClient streetClient;
    private final MediasClient mediasClient;

    public AnnounceSearchService(ElasticsearchClient elasticsearchClient, StreetClient streetClient, MediasClient mediasClient) {
        this.elasticsearchClient = elasticsearchClient;
        this.streetClient = streetClient;
        this.mediasClient = mediasClient;
    }

    /**
     * @param query
     * @param category
     * @param sortBy
     * @param order
     * @param page
     * @param size
     * @return
     */
    @Override
    public Map<String, Object> searchAnnounces(String query, String category, String sortBy, SortOrder order, int page, int size) throws IOException {
        SearchResponse<AnnounceDocument> response = this.elasticsearchClient.search(s -> s
                        .index("announces")
                        .query(q -> q.bool(b -> {
                            if (query != null) {
                                b.must(bm ->bm.bool(sub -> {
                                    sub.should(s1 -> s1.match(m -> m.field("title").query(query).boost(1f)))
                                            .should(s2 -> s2.match(m -> m.field("description").query(query).boost(1f)))
                                            .should(s3 -> s3.nested(n -> n
                                            .path("fields")
                                            .query(nq -> nq.bool(nb -> nb
                                                    .should(ms1 -> ms1.match(m -> m.field("fields.name").query(query).boost(1.5f)))
                                                    .should(ms->ms.bool(bl->bl.must(msd->msd.exists(ex->ex.field("fields.dataValue"))).must(mss->mss.match(mt->mt.field("fields.dataValue").query(query)))))
                                                   // .should(ms2 -> ms2.match(m -> m.field("fields.dataValue").query(query).boost(3f)))
                                            ))
                                    ))

                                            .should(s4 -> s4.match(m ->m.field("typeName").query(query)))
                                            .should(s5 -> s5.match(m ->m.field("address").query(query)))
                                            .should(s6 -> s6.match(m ->m.field("categoryTitle").query(query)));

                                    return  sub;
                                }));

                            }
                            if (category != null) {
                                b.must(m -> m.match(t -> t.field("categoryTitle").query(category)));
                            }
                            return b;
                        }))
                        .source(src -> src.filter(f -> f.includes("id", "title", "description","typeName", "address","tel","price","categoryTitle","postedAt","streetId", "location")))
                        .sort(so -> {
                            if(sortBy!= null)return so.field(f -> f.field(sortBy).order(order));
                            return so.score(st->st.order(SortOrder.Desc));
                        })
                        .from(page * size)
                        .size(size),
                AnnounceDocument.class);

        List<AnnounceDocument> announces = response.hits().hits().stream()
                .map(Hit::source)
                .toList();
        //add Media et street location to each announce if not empty
        if (!announces.isEmpty())addRemoteResources(announces);
        long total = 0 ;
        try {
            total = response.hits().total().value();
        }catch (NullPointerException ignored){
            ignored.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalElements", total);
        result.put("page", page);
        result.put("totalPages",(int) Math.ceil(((double) total /size)));
        result.put("size", size);
        result.put("content", announces);
        result.put("query", query);

        return result;
    }

    /**
     * @return
     */
    @Override
    public Map<String, Long> getCategoryCounts() {
        return Map.of();
    }

    private void addRemoteResources(List<AnnounceDocument> documents){
        documents.forEach(doc-> {
            List<Media> medias = this.mediasClient.getAdvertMedias(doc.getId());
            Street street = this.streetClient.getStreet(doc.getStreetId());
            doc.setMedias(medias);
            doc.setStreet(street);
        });
    }
}
