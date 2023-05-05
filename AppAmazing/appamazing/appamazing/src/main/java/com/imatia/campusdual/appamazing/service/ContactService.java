package com.imatia.campusdual.appamazing.service;

import com.imatia.campusdual.appamazing.api.IContactService;
import com.imatia.campusdual.appamazing.model.Contact;
import com.imatia.campusdual.appamazing.model.dao.ContactDAO;
import com.imatia.campusdual.appamazing.model.dto.ContactDTO;
import com.imatia.campusdual.appamazing.model.dto.dtomapper.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ContactService")
@Lazy
public class ContactService implements IContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Override
    public ContactDTO querycontact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        return ContactMapper.INSTANCE.toDto(contactDAO.getReferenceById(contact.getId()));
    }

    @Override
    public List<ContactDTO> queryAll() {
        return ContactMapper.INSTANCE.toDtoList(contactDAO.findAll());
    }

    @Override
    public int insertContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        contactDAO.saveAndFlush(contact);
        return contact.getId();
    }

    @Override
    public int updateContact(ContactDTO contactDTO) {
        return insertContact(contactDTO);
    }

    @Override
    public int deleteContact(ContactDTO contactDTO) {
        Contact contact = ContactMapper.INSTANCE.toEntity(contactDTO);
        contactDAO.delete(contact);
        return contactDTO.getId();
    }
}
