package org.mts.usersservice.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mts.usersservice.entities.Client;
import org.mts.usersservice.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientService implements IClientService{

    @Autowired
    private ClientRepository clientRepository;
    /**
     * @param client
     * @return
     */
    @Override
    public Client creatClient(Client client) {
        return this.clientRepository.save(client);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Client getClientById(String id) {
        return this.clientRepository.findById(id).orElseThrow();
    }

    /**
     * @param client
     * @return
     */
    @Override
    public Client updateClient(Client client) {
        return this.clientRepository.save(client);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Client deleteClientById(String id) {
       Client client = this.clientRepository.findById(id).orElseThrow();
       this.clientRepository.deleteById(id);
        return client;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Client> getClients(Pageable pageable) {
        return this.clientRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Client> getListClients() {
        return this.clientRepository.findAll();
    }

    /**
     * @param firstname
     * @param pageable
     * @return
     */
    @Override
    public Page<Client> findClientsByFirstname(String firstname, Pageable pageable) {
        return null;
    }
}
