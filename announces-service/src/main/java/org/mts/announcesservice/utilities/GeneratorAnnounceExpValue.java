package org.mts.announcesservice.utilities;

import org.mts.announcesservice.entities.Announce;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

public class GeneratorAnnounceExpValue implements IGeneratorAnnounceExpValue {
    /**
     * @param announce
     * @return
     */
    @Override
    public String generateExpValue(Announce announce) {


        return "";
    }

    /**
     * @param announce
     * @return
     */
    @Override
    public boolean isExpired(Announce announce) {
        return false;
    }
}
