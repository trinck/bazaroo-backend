package org.mts.announcesservice.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.GeoDistanceType;
import co.elastic.clients.elasticsearch._types.LatLonGeoLocation;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mts.announcesservice.clients.MediasClient;
import org.mts.announcesservice.clients.StreetClient;
import org.mts.announcesservice.configs.CountryContext;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.documents.SavedSearchDocument;
import org.mts.announcesservice.dtos.SavedSearchInputDto;
import org.mts.announcesservice.dtos.SavedSearchRequest;
import org.mts.announcesservice.dtos.SearchRequestDTO;
import org.mts.announcesservice.entities.SavedSearch;
import org.mts.announcesservice.enums.SavedSearchScheduleType;
import org.mts.announcesservice.enums.SearchTypes;
import org.mts.announcesservice.repositories.SavedSearchRepository;
import org.mts.announcesservice.utilities.ElasticSearchDynamicQueryGenerator;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AnnounceSearchService implements IAnnounceSearchService {

    private static final Logger logger = LoggerFactory.getLogger(AnnounceSearchService.class);

    private final SchedulerService schedulerService;
    private final ElasticsearchClient elasticsearchClient;
    private final SavedSearchRepository savedSearchRepository;
    private final ElasticsearchOperations operations;
    private final MediasClient mediasClient;
    private final StreetClient streetClient;
    @Value("${app.quartz.jobs.id.prefix}")
    private String JOB_PREFIX_ID;

    public AnnounceSearchService(SchedulerService schedulerService, ElasticsearchClient elasticsearchClient, StreetClient streetClient, MediasClient mediasClient, SavedSearchRepository savedSearchRepository, ElasticsearchOperations operations, IAuthService iAuthService, MediasClient mediasClient1, StreetClient streetClient1) {
        this.schedulerService = schedulerService;
        this.elasticsearchClient = elasticsearchClient;
        this.savedSearchRepository = savedSearchRepository;
        this.operations = operations;
        this.mediasClient = mediasClient1;
        this.streetClient = streetClient1;
    }

    /**
     * @param query
     * @param category
     * @param sortBy
     * @param order
     * @param page
     * @param size
     * @param dateBegin
     * @param userId
     * @param tenantId
     * @return
     */
    @Override
    public Map<String, Object> searchAnnounces(String query, String category, String sortBy, SortOrder order, int page, int size, Date dateBegin, String userId, String tenantId) throws IOException {
        SearchResponse<AnnounceDocument> response = this.elasticsearchClient.search(s -> s
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

                            if (userId != null) {

                                b.filter(f -> f.bool(fb -> fb.mustNot(m -> m.term(tm -> tm.field("userId").value(userId)))));
                            }

                            b.filter(f -> f.bool(bo -> bo.must(fm -> fm.term(t -> t.field("tenantId").value(tenantId)))));
                            return b;
                        }))
                        .source(src -> src.filter(f -> f.includes("id", "title", "description", "typeName", "address", "tel", "price", "categoryTitle", "postedAt", "streetId", "location", "userId")))
                        .sort(so -> {
                            if (sortBy != null) return so.field(f -> f.field(sortBy).order(order));
                            return so.score(st -> st.order(SortOrder.Desc));
                        })
                        .from(page * size)
                        .size(size),
                AnnounceDocument.class);

        List<AnnounceDocument> announces = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        // set medias
        this.addRemoteResource(announces);


        long total = 0;
        try {
            total = response.hits().total().value();
        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalElements", total);
        result.put("page", page);
        result.put("totalPages", (int) Math.ceil(((double) total / size)));
        result.put("size", size);
        result.put("content", announces);
        result.put("query", query);

        return result;
    }


    @Override
    public Map<String, Object> searchAnnounces(SearchRequestDTO searchRequest) throws IOException {
        SearchResponse<AnnounceDocument> response = this.elasticsearchClient.search(s -> s
                        .index("announces")
                        .query(q -> q.bool(b -> {
                            if (searchRequest.getQuery() != null && !searchRequest.getQuery().isEmpty()) {
                                b.must(bm -> bm.bool(sub -> {
                                    sub.should(s1 -> s1.match(m -> m.field("title").query(searchRequest.getQuery()).boost(1f)))
                                            .should(s2 -> s2.match(m -> m.field("description").query(searchRequest.getQuery()).boost(1f)))
                                            .should(s3 -> s3.nested(n -> n
                                                    .path("fields")
                                                    .query(nq -> nq.bool(nb -> nb
                                                            .should(ms1 -> ms1.match(m -> m.field("fields.name").query(searchRequest.getQuery()).boost(1.5f)))
                                                            .should(ms1 -> ms1.match(m -> m.field("fields.dataValueString").query(searchRequest.getQuery()).boost(1.5f)))
                                                    ))
                                            ))

                                            .should(s4 -> s4.match(m -> m.field("typeName").query(searchRequest.getQuery())))
                                            .should(s5 -> s5.match(m -> m.field("address").query(searchRequest.getQuery())));

                                    return sub;
                                }));

                            }
//
                            // 🔥 Ajout des filtres
                            // 🔁 Filtres dynamiques
                            if (searchRequest.getFilters() != null) {
                                for (Map.Entry<String, Object> entry : searchRequest.getFilters().entrySet()) {
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
                                    else if (value instanceof List<?> listValue && (key.equalsIgnoreCase("streetId") || key.equalsIgnoreCase("cityId")) && searchRequest.getSearchBy() == SearchTypes.REGIONS_FILTER) {
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
                                                }))
                                        ));
                                    }

                                    // ⚙️ Filtres simples (term)
                                    else {
                                        b.must(m -> m.match(t -> t.field(key).query(value.toString())));
                                    }
                                }
                            }

                            // 🎯 Ajout dynamique du filtre géo radius
                            if (searchRequest.getSearchBy() != null && searchRequest.getLocationsMode() != null && searchRequest.getSearchBy() != SearchTypes.REGIONS_FILTER) {
                                Map<String, Object> locationMode = searchRequest.getLocationsMode();
                                if (searchRequest.getSearchBy() == SearchTypes.NEAR_BY) {
                                    Map<String, Object> center = (Map<String, Object>) locationMode.get("center");
                                    b.must(fm -> fm.geoDistance(g -> g
                                            .field("location")
                                            .distanceType(GeoDistanceType.Arc)
                                            .distance(locationMode.get("radius").toString())
                                            .location(l -> l.latlon(lt -> lt.lat((double) center.get("lat")).lon((double) center.get("lon"))))
                                    ));
                                } else if (searchRequest.getSearchBy() == SearchTypes.USER_MAP_MOVE) {
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


                            //init tenantId if difere research with searchRequest
                            String tenantId = searchRequest.getTenantId();
                            b.filter(f -> f.bool(bo -> bo.must(fm -> fm.term(t -> t.field("tenantId").value(tenantId == null? CountryContext.getCountry():tenantId)))));
                            return b;
                        }))
                        .source(src -> src.filter(f -> f.includes("id","status", "title", "description", "typeName", "address", "tel", "price", "categoryTitle", "postedAt", "streetId", "location", "userId", "views", "clicks", "impressions")))
                        .sort(so -> {
                            SortOrder sortOrder = "ASC".equalsIgnoreCase(searchRequest.getOrder()) ? SortOrder.Asc : SortOrder.Desc;
                            if (searchRequest.getSortBy() != null) {
                                return so.field(f -> f.field(searchRequest.getSortBy()).order(sortOrder));
                            }
                            return so.score(st -> st.order(sortOrder));
                        })
                        .from(searchRequest.getPage() * searchRequest.getSize())
                        .size(searchRequest.getSize()),
                AnnounceDocument.class);

        List<AnnounceDocument> announces = response.hits().hits().stream()
                .map(Hit::source)
                .toList();

        // set medias
        this.addRemoteResource(announces);


        long total = 0;
        try {
            total = response.hits().total().value();
        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalElements", total);
        result.put("page", searchRequest.getPage());
        result.put("totalPages", (int) Math.ceil(((double) total / searchRequest.getSize())));
        result.put("size", searchRequest.getSize());
        result.put("content", announces);
        result.put("query", searchRequest.getQuery());

        return result;
    }


    private void addRemoteResource(List<AnnounceDocument> announces) {
        announces.forEach(ad -> {
            ad.setMedias(this.mediasClient.getAdvertMedias(ad.getId()));
            ad.setStreet(this.streetClient.getStreet(ad.getStreetId()));
        });
    }


    /**
     * @return
     */
    @Override
    public Map<String, Long> getCategoryCounts() {
        return Map.of();
    }

    /**
     * @param searchToSave
     * @return
     */
    @Override
    public SavedSearch saveSearch(SavedSearchInputDto searchToSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SavedSearch search = new SavedSearch();
        search.setSearchName(searchToSave.getSearchName());
        search.setActive(searchToSave.getActive());
        search.setUserId(authentication.getName());
        search.setCreatedAt(new Date());
        search.setBeginDate(new Date());
        search.setTime(searchToSave.getTime());
        search.setType(searchToSave.getType());


        try {
            search.setCriteria(new ObjectMapper().writeValueAsString(searchToSave.getCriteria()));
            search = this.savedSearchRepository.save(search);
            this.persistSavedSearchDoc(search, searchToSave.getCriteria());
            if (search.getType() != SavedSearchScheduleType.IMMEDIATE && search.getActive()) {
                this.schedulerService.scheduleSearch(search, search.getTime());
            }
            return search;
        } catch (JsonProcessingException | SchedulerException e) {
            if (e instanceof SchedulerException) {
                this.deleteSavedSearchById(search.getId());
            }
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param searchToSave
     * @return
     */
    @Override
    public SavedSearch saveSearch(SavedSearchRequest searchToSave) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SavedSearch search = new SavedSearch();
        search.setSearchName(searchToSave.getSearchName());
        search.setActive(searchToSave.getActive());
        search.setUserId(authentication.getName());
        search.setCreatedAt(new Date());
        search.setBeginDate(new Date());
        search.setTime(searchToSave.getTime());
        search.setType(searchToSave.getType());

        try {
            search.setCriteria(new ObjectMapper().writeValueAsString(searchToSave.getCriteria()));
            search = this.savedSearchRepository.save(search);
            this.persistSavedSearchDoc(search, searchToSave);
            if (search.getType() != SavedSearchScheduleType.IMMEDIATE && search.getActive()) {
                this.schedulerService.scheduleSearch(search, search.getTime());
            }
            return search;
        } catch (JsonProcessingException | SchedulerException e) {
            if (e instanceof SchedulerException) {
                this.deleteSavedSearchById(search.getId());
            }
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param savedSearch
     * @return
     */
    @Override
    public SavedSearch update(SavedSearch savedSearch) {

        try {
            savedSearch = this.savedSearchRepository.save(savedSearch);
            SearchRequestDTO searchRequestDTO = new ObjectMapper().readValue(savedSearch.getCriteria(), SearchRequestDTO.class);
            SavedSearchRequest savedSearchRequest = SavedSearchRequest.builder()
                    .criteria(searchRequestDTO)
                    .searchName(savedSearch.getSearchName())
                    .time(savedSearch.getTime())
                    .active(savedSearch.getActive())
                    .type(savedSearch.getType())
                    .id(savedSearch.getId())
                    .tenantId(savedSearch.getTenantId())
                    .build();
            this.persistSavedSearchDoc(savedSearch, savedSearchRequest);

            if (this.schedulerService.jobExists(savedSearch.getId())) {
                this.schedulerService.deleteNotification(savedSearch.getId());
            }

            if (savedSearch.getType() != SavedSearchScheduleType.IMMEDIATE && savedSearch.getActive()) {
                // then, it can't be executed immediately
                Thread.sleep(1000);
                // ======================
                this.schedulerService.scheduleSearch(savedSearch, savedSearch.getTime());
            }
            return savedSearch;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void persistSavedSearchDoc(SavedSearch search, Map<String, Object> criteria) {
        String category = criteria.get("category") != null ? (String) criteria.get("category") : null;
        String query = (String) criteria.get("query");

        // construct query percolator and SavedSearchDocument if
        ElasticSearchDynamicQueryGenerator dynamicQueryGenerator = new ElasticSearchDynamicQueryGenerator();
        SearchRequest searchRequest = dynamicQueryGenerator.buildSearchRequest(query, category, null, SortOrder.Desc, 0, 5, null, search.getTenantId());

        JsonData searchJsonData = JsonData.of(searchRequest.query());
        // Créez un JsonData en fournissant le mapper
        //JsonpMapper mapper = this.elasticsearchClient._transport().jsonpMapper();
        try {
            Map<String, Object> queryDsl = (Map<String, Object>) searchJsonData.to(Map.class, new JacksonJsonpMapper());
            SavedSearchDocument searchDocument = SavedSearchDocument.builder()
                    .id(this.JOB_PREFIX_ID + search.getId())
                    .userId(search.getUserId())
                    .active(search.getActive())
                    .type(search.getType().name())
                    .query(queryDsl)
                    .tenantId(search.getTenantId())
                    .build();

            this.operations.save(searchDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void persistSavedSearchDoc(SavedSearch search, SavedSearchRequest searchRequestDto) {

        // construct query percolator and SavedSearchDocument if
        ElasticSearchDynamicQueryGenerator dynamicQueryGenerator = new ElasticSearchDynamicQueryGenerator();
        SearchRequest searchRequest = dynamicQueryGenerator.buildSearchRequest(searchRequestDto, searchRequestDto.getCriteria(), search.getTenantId(), 10, 0);

        JsonData searchJsonData = JsonData.of(searchRequest.query());
        // Créez un JsonData en fournissant le mapper
        //JsonpMapper mapper = this.elasticsearchClient._transport().jsonpMapper();
        try {
            Map<String, Object> queryDsl = (Map<String, Object>) searchJsonData.to(Map.class, new JacksonJsonpMapper());
            SavedSearchDocument searchDocument = SavedSearchDocument.builder()
                    .id(this.JOB_PREFIX_ID + search.getId())
                    .userId(search.getUserId())
                    .active(search.getActive())
                    .type(search.getType().name())
                    .query(queryDsl)
                    .tenantId(search.getTenantId())
                    .build();

            this.operations.save(searchDocument);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param userId
     */
    @Override
    public void deleteAllSavedSearchByUserId(String userId) {

        List<SavedSearch> allSavedSearches = this.savedSearchRepository.findAllByUserId(userId);
        if (allSavedSearches.isEmpty()) {
            return;
        }
        List<Long> ids = allSavedSearches.stream().map(SavedSearch::getId).toList();
        try {
            this.schedulerService.deleteAllNotifications(ids);
            this.savedSearchRepository.deleteAllByUserId(userId);
            Criteria criteria = Criteria.where("userId").is(userId);
            Query query = new CriteriaQuery(criteria);
            this.operations.delete(query, SavedSearchDocument.class);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * @param id
     * @return
     */
    @Override
    public SavedSearch getSavedSearchByIdAndUserId(Long id, String userId) {
        return this.savedSearchRepository.findByIdAndUserId(id, userId).orElseThrow();
    }

    public void deleteSavedSearchById(Long id) {
        try {
            SavedSearch search = this.savedSearchRepository.findById(id).orElseThrow();
            if (this.schedulerService.jobExists(search.getId())) {
                this.schedulerService.deleteNotification(search.getId());
            }
            this.savedSearchRepository.deleteById(id);
            this.operations.delete(this.JOB_PREFIX_ID + id, SavedSearchDocument.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
