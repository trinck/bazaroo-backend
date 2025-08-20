package org.mts.gatewayservice.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime timestamp = OffsetDateTime.now();

    private String remoteAddr;
    @Column(length = 512) private String userAgent;

    @Column(length = 16) private String method;
    @Column(length = 1024) private String path;

    @Column(length = 255) private String targetServiceId;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String queryParamsJson;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String headersJson;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String requestBody;

    private Integer responseStatus;

    @Lob @Column(columnDefinition = "LONGTEXT")
    private String responseBody;

    private Long durationMs;
}
