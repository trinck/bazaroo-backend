package org.mts.announcesservice.utilities;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.JsonData;
import org.mts.announcesservice.configs.CountryContext;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.dtos.SavedSearchRequest;
import org.mts.announcesservice.dtos.SearchRequestDTO;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.SearchTypes;
import org.springframework.lang.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class ElasticSearchDynamicQueryGenerator {

    public SearchRequest buildSearchRequest(String query, String category, String sortBy, SortOrder order, int page, int size, Date dateBegin, @NonNull String tenantId) {
        return SearchRequest.of(s -> s
                .index("announces")
                .query(q -> q.bool(b -> {
                    if (query != null) {
                        b.must(bm -> bm.bool(sub -> {
                            sub.should(s1 -> s1.match(m -> m.field("title").query(query).boost(1f)))
                                    .should(s2 -> s2.match(m -> m.field("description").query(query).boost(1f)))
                                    .should(s3 -> s3.nested(n -> n
                                            .path("fields")
                                            .query(nq -> nq.bool(nb -> nb
                                                            .should(ms1 -> ms1.match(m -> m.field("fields.name").query(query).boost(1.5f)))
                                                            .should(ms1 -> ms1.match(m -> m.field("fields.dataValueString").query(query).boost(1.5f)))
                                                    //.should(ms -> ms.bool(bl -> bl.must(msd -> msd.exists(ex -> ex.field("fields.dataValue"))).must(mss -> mss.match(mt -> mt.field("fields.dataValue").query(query)))))
                                                    // .should(ms2 -> ms2.match(m -> m.field("fields.dataValue").query(query).boost(3f)))
                                            ))
                                    ))

                                    .should(s4 -> s4.match(m -> m.field("typeName").query(query)))
                                    .should(s5 -> s5.match(m -> m.field("address").query(query)))
                                    .should(s6 -> s6.match(m -> m.field("categoryTitle").query(query)));

                            return sub;
                        }));

                    }
                    if (category != null) {
                        b.must(m -> m.match(t -> t.field("categoryTitle").query(category)));
                    }
                    // 🔥 Ajout du filtre sur la date (ex: récupérer les annonces postées après `dateFilter`)
                    if (dateBegin != null) {
                        // Convertir Date en format Elasticsearch ISO 8601
                        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String dateFilterString = isoFormat.format(dateBegin);
                        // Convertir String en JsonData pour Elasticsearch
                        JsonData jsonDate = JsonData.of(dateFilterString);
                        b.filter(f -> f.range(r -> r.field("postedAt").gt(jsonDate)));
                    }
                    b.filter(f -> f.bool(bo -> bo.must(fm -> fm.term(t -> t.field("tenantId").value(tenantId)))));
                    return b;
                }))
                .source(src -> src.filter(f -> f.includes("id", "title", "description", "typeName", "address", "tel", "price", "categoryTitle", "postedAt", "streetId", "location")))
                .sort(so -> {
                    if (sortBy != null) return so.field(f -> f.field(sortBy).order(order));
                    return so.score(st -> st.order(SortOrder.Desc));
                })
                .from(page * size)
                .size(size)
        );
    }


    public SearchRequest buildSearchRequest(SavedSearchRequest savedSearch, SearchRequestDTO criteria, @NonNull String tenantId, int size, int page) {

        String query = criteria.getQuery();
        SearchTypes searchType = criteria.getSearchBy();
        return SearchRequest.of(s -> s
                .index("announces")
                .query(q -> q.bool(b -> {
                    if (query != null && !query.isEmpty()) {
                        b.must(bm -> bm.bool(sub -> {
                            sub.should(s1 -> s1.match(m -> m.field("title").query(query).boost(1f)))
                                    .should(s2 -> s2.match(m -> m.field("description").query(query).boost(1f)))
                                    .should(s3 -> s3.nested(n -> n
                                            .path("fields")
                                            .query(nq -> nq.bool(nb -> nb
                                                    .should(ms1 -> ms1.match(m -> m.field("fields.name").query(query).boost(1.5f)))
                                                    .should(ms1 -> ms1.match(m -> m.field("fields.dataValueString").query(query).boost(1.5f)))
                                            ))
                                    ))

                                    .should(s4 -> s4.match(m -> m.field("typeName").query(query)))
                                    .should(s5 -> s5.match(m -> m.field("address").query(query)));

                            return sub;
                        }));

                    }


                    // 🔥 Ajout des filtres
                    // 🔁 Filtres dynamiques
                    if (criteria.getFilters() != null) {
                        for (Map.Entry<String, Object> entry : criteria.getFilters().entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();

                            if (value == null || value.toString().isBlank()) continue;

                            // 🔢 Filtres par intervalle
                            if (key.endsWith("Min") || key.endsWith("Max")) {
                                b.must(f -> {

                                    if (key.endsWith("Min")) {
                                        f.range(r -> r.field(key.substring(0, key.length() - "Min".length())).gte(JsonData.of(value)));
                                    } else if (key.endsWith("Max")) {
                                        f.range(r -> r.field(key.substring(0, key.length() - "Min".length())).lte(JsonData.of(value)));
                                    }
                                    return f;
                                });
                            } else if (value instanceof Map<?, ?> mapValue && (mapValue.containsKey("min") && mapValue.containsKey("max"))) {
                                b.must(qr -> qr.range(r -> r.field(key).gte(JsonData.of(mapValue.get("min"))).lte(JsonData.of(mapValue.get("max")))
                                ));
                            }


                            // 🎯 Filtres multi-valeurs (terms) for cities et street
                            else if (value instanceof List<?> listValue && (key.equalsIgnoreCase("streetId") || key.equalsIgnoreCase("cityId")) && searchType == SearchTypes.REGIONS_FILTER) {
                                b.should(f -> f.terms(t -> t.field(key).terms(ts -> ts.value(
                                        listValue.stream().map(v -> FieldValue.of(v.toString())).toList()
                                ))));
                                b.minimumShouldMatch("1");

                            }

                            // 🎯 Filtres multi-valeurs (terms) pour catégories ex: computer
                            else if (value instanceof List<?> listValue) {
                                b.must(f -> f.terms(t -> t.field(key).terms(ts -> ts.value(
                                        listValue.stream().map(v -> FieldValue.of(v.toString())).toList()
                                ))));

                            }

                            // 🧬 Filtres nested (ex: fields.name / fields.dataValue)
                            else if (key.startsWith("fields.")) {
                                b.must(f -> f.nested(n -> n
                                        .path("fields")
                                        .query(nq -> nq.bool(nb -> {
                                                    nb.must(mm -> mm.match(m -> m.field("fields.name").query(key.substring("fields.".length()))));
                                                    if (value instanceof Double) {
                                                        nb.must(m -> m.term(t -> t.field("fields.dataValueDouble").value((Double) value)));
                                                    } else if (value instanceof String) {
                                                        nb.must(m -> m.match(t -> t.field("fields.dataValueString").query((String) value)));
                                                    } else if (value instanceof Boolean) {
                                                        nb.must(m -> m.term(t -> t.field("fields.dataValueBoolean").value((Boolean) value)));
                                                    }
                                                    return nb;
                                                }
                                        ))
                                ));
                            }

                            // ⚙️ Filtres simples (term)
                            else {
                                b.must(m -> m.match(t -> t.field(key).query(value.toString())));
                            }
                        }
                    }

                    // 🎯 Ajout dynamique du filtre géo radius
                    if (criteria.getLocationsMode() != null && searchType != SearchTypes.REGIONS_FILTER) {
                        Map<String, Object> locationMode = criteria.getLocationsMode();
                        if (criteria.getSearchBy() == SearchTypes.NEAR_BY) {
                            Map<String, Object> center = (Map<String, Object>) locationMode.get("center");
                            b.must(fm -> fm.geoDistance(g -> g
                                    .field("location")
                                    .distanceType(GeoDistanceType.Arc)
                                    .distance(locationMode.get("radius").toString())
                                    .location(l -> l.latlon(lt -> lt.lat((double) center.get("lat")).lon((double) center.get("lon"))))
                            ));
                        } else if (criteria.getSearchBy() == SearchTypes.USER_MAP_MOVE) {
                            Map<String, Object> topRight = (Map<String, Object>) locationMode.get("northeast");
                            Map<String, Object> bottomLeft = (Map<String, Object>) locationMode.get("southwest");
                            b.must(fm -> fm.geoBoundingBox(g -> g
                                    .field("location")
                                    .boundingBox(bb -> bb
                                            .trbl(v -> v.bottomLeft(br -> br.latlon(bbb -> bbb.lat((double) bottomLeft.get("lat")).lon((double) bottomLeft.get("lon"))))
                                                    .topRight(tr -> tr.latlon(bbb -> bbb.lat((double) topRight.get("lat")).lon((double) topRight.get("lon")))

                                                    )
                                            ))));
                        }
                    }
                    // 🎯 Ajout dynamique du filtre géo bounding box


                    b.filter(f -> f.bool(bo -> bo.must(fm -> fm.term(t -> t.field("tenantId").value(tenantId)))));
                    return b;
                }))
                .source(src -> src.filter(f -> f.includes("id", "title", "description", "typeName", "address", "tel", "price", "categoryTitle", "postedAt", "streetId", "location")))
                .sort(so -> {
                    SortOrder sortOrder = "ASC".equalsIgnoreCase((String) criteria.getOrder()) ? SortOrder.Asc : SortOrder.Desc;
                    if (criteria.getSortBy() != null) {
                        return so.field(f -> f.field((String) criteria.getSortBy()).order(sortOrder));
                    }
                    return so.score(st -> st.order(sortOrder));
                })
                .from(page * size)
                .size(size));
    }

}
