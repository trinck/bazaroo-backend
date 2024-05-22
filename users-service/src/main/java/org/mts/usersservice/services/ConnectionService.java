package org.mts.usersservice.services;

import org.mts.usersservice.entities.Connection;
import org.mts.usersservice.repositories.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConnectionService implements IConnectionService{

    @Autowired
   private ConnectionRepository connectionRepository;

    /**
     * @param connection
     * @return
     */
    @Override
    public Connection creatConnection(Connection connection) {
        return this.connectionRepository.save(connection);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Connection getConnectionById(Long id) {
        return this.connectionRepository.findById(id).orElseThrow();
    }

    /**
     * @param connection
     * @return
     */
    @Override
    public Connection updateConnection(Connection connection) {
        return this.connectionRepository.save(connection);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Connection deleteConnectionById(Long id) {

        Connection connection = this.connectionRepository.findById(id).orElseThrow();
        this.connectionRepository.deleteById(id);
        return connection;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Connection> getConnections(Pageable pageable) {
        return this.connectionRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Connection> getListConnections() {
        return this.connectionRepository.findAll();
    }
}
