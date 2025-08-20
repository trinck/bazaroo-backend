package org.mts.gatewayservice.repositories;

import org.mts.gatewayservice.entities.GatewayLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


public interface GatewayLogRepository extends JpaRepository<GatewayLog, Long>, JpaSpecificationExecutor<GatewayLog> {

}
