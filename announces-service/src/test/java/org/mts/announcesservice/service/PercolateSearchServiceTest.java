package org.mts.announcesservice.service;

import co.elastic.clients.json.JsonData;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.mts.announcesservice.documents.AnnounceDocument;
import org.mts.announcesservice.entities.Announce;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PercolateSearchServiceTest {

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PercolateSearchService percolateSearchService;

    public PercolateSearchServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnnounceDocumentMappingToJsonData() {
        // Arrange
        Announce announce = Announce.builder()
                .id("123")
                .userId("user1")
                .title("Sample Title")
                .build();


        AnnounceDocument mappedDocument = new AnnounceDocument();
        mappedDocument.setId("123");
        mappedDocument.setUserId("user1");
        mappedDocument.setTitle("Sample Title");

        when(modelMapper.map(any(Announce.class), any(Class.class))).thenReturn(mappedDocument);

        // Act
        JsonData jsonData = JsonData.of(modelMapper.map(announce, AnnounceDocument.class));

        // Assert
        AnnounceDocument mappedResult = jsonData.to(AnnounceDocument.class);
        assertEquals(mappedDocument.getId(), mappedResult.getId());
        assertEquals(mappedDocument.getUserId(), mappedResult.getUserId());
        assertEquals(mappedDocument.getTitle(), mappedResult.getTitle());
    }
}