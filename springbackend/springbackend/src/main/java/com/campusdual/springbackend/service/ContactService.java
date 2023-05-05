package com.campusdual.springbackend.service;

import com.campusdual.springbackend.api.IContactService;
import com.campusdual.springbackend.model.Contact;
import com.campusdual.springbackend.model.dao.ContactDao;
import com.campusdual.springbackend.model.dto.ContactDTO;
import com.campusdual.springbackend.model.dto.dtomapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ContactService")
@Lazy
public class ContactService implements IContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public ContactDTO queryContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        return ContactMapper.INSTANCE.toDTO(contactDao.getReferenceById(contact.getId()));
    }

    @Override
    public List<ContactDTO> queryAllContact() {
        return ContactMapper.INSTANCE.toDTOList(contactDao.findAll());
    }

    @Override
    public int insertContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        contactDao.saveAndFlush(contact);
        return contact.getId();
    }

    @Override
    public int updateContact(ContactDTO contactDTO) {
        // The save() method of JPARepository performs both insertion and update. Insert if no primary key is provided, update otherwise. For this reason, the method #insertContact() is called
        return insertContact(contactDTO);
    }

    @Override
    public int deleteContact(ContactDTO contactDTO) {
        int id = contactDTO.getId();
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        contactDao.delete(contact);
        return id;
    }
}
