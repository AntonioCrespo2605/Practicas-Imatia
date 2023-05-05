package com.imatia.campusdual.appamazing.api;

import com.imatia.campusdual.appamazing.model.dto.ContactDTO;

import java.util.List;

public interface IContactService {
    ContactDTO querycontact(ContactDTO contactDTO);

    List<ContactDTO> queryAll();

    int insertContact(ContactDTO contactDTO);

    int updateContact(ContactDTO contactDTO);

    int deleteContact(ContactDTO contactDTO);
    
}
