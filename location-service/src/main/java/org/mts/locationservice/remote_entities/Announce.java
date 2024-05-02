package org.mts.locationservice.remote_entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announce {

    private String id;
    public String address;
    /** @pdOid fb5a5ff3-39cc-402c-9e42-2bf0d8ba53ec */
    public Double price;
    /** @pdOid 5ad7cf2e-dd86-4d1d-8425-9f41157acae8 */
    public String title;
    /** @pdOid 30bf1bc8-4326-409c-b544-837c1384a78d */
    public String description;
    /** @pdOid 3fc91822-033d-4d5d-9e44-197c16d66f38 */
    public Date publishedAt;
    /** @pdOid 346bec52-54aa-4712-a595-988b93dc995d */
    public String status;
}
