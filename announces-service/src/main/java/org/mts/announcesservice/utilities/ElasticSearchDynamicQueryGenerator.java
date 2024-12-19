package org.mts.announcesservice.utilities;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;

public class ElasticSearchDynamicQueryGenerator {

    public SearchRequest buildSearchRequest(String query, String category, String sortBy, SortOrder order, int page, int size) {
        return SearchRequest.of(s -> s
                .index("announces")
                .query(q -> q.bool(b -> {
                    if (query != null) {
                        b.should(s1 -> s1.match(m -> m.field("title").query(query)))
                                .should(s2 -> s2.match(m -> m.field("description").query(query)));
                    }
                    if (category != null) {
                        b.must(m -> m.match(mq -> mq.field("category").query(category)));
                    }
                    return b;
                }))
                .sort(so -> so.field(f -> f.field(sortBy).order(order)))
                .from(page * size)
                .size(size)
        );
    }

}
