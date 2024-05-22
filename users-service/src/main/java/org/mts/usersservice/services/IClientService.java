package org.mts.usersservice.services;

import org.mts.usersservice.entities.Client;
import org.mts.usersservice.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClientService {


    public Client creatClient(Client client);
    public Client getClientById(String id);
    public Client updateClient(Client client);
    public Client deleteClientById(String id);
    public Page<Client> getClients(Pageable pageable);
    public List<Client> getListClients();
    public Page<Client> findClientsByFirstname(String firstname, Pageable pageable);
}
