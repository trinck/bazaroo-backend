package org.mts.usersservice.services;

import org.mts.usersservice.entities.Contact;
import org.mts.usersservice.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService implements IContactService{

    @Autowired
   private ContactRepository contactRepository;

    /**
     * @param contact
     * @return
     */
    @Override
    public Contact creatContact(Contact contact) {
        return this.contactRepository.save(contact);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Contact getContactById(Long id) {
        return this.contactRepository.findById(id).orElseThrow();
    }

    /**
     * @param contact
     * @return
     */
    @Override
    public Contact updateContact(Contact contact) {
        return this.contactRepository.save(contact);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Contact deleteContactById( Long id) {

        Contact contact = this.contactRepository.findById(id).orElseThrow();
        this.contactRepository.deleteById(id);

        return contact;
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<Contact> getContacts(Pageable pageable) {
        return this.contactRepository.findAll(pageable);
    }

    /**
     * @return
     */
    @Override
    public List<Contact> getListContacts() {
        return this.contactRepository.findAll();
    }
}
