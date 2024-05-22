package org.mts.usersservice.services;

import org.mts.usersservice.entities.Connection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IConnectionService {

    public Connection creatConnection(Connection connection);
    public Connection getConnectionById(Long id);
    public Connection updateConnection(Connection connection);
    public Connection deleteConnectionById(Long id);
    public Page<Connection> getConnections(Pageable pageable);
    public List<Connection> getListConnections();

}
