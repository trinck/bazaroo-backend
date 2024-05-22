package org.mts.usersservice.services;

import org.mts.usersservice.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IContactService {

    public Contact creatContact(Contact contact);
    public Contact getContactById(Long id);
    public Contact updateContact(Contact contact);
    public Contact deleteContactById(Long id);
    public Page<Contact> getContacts(Pageable pageable);
    public List<Contact> getListContacts();

}
