package org.mts.announcesservice.utilities;

import org.mts.announcesservice.entities.Announce;


/**
 * Contains methods those allow to generate token expiration value
 * and verification for {@link Announce}
 */
public interface IGeneratorAnnounceExpValue {

    public String generateExpValue(Announce announce);
    public boolean isExpired(Announce announce);
}
