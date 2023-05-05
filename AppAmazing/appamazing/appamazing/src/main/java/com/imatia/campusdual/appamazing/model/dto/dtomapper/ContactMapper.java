package com.imatia.campusdual.appamazing.model.dto.dtomapper;

import com.imatia.campusdual.appamazing.model.Contact;
import com.imatia.campusdual.appamazing.model.dto.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDTO toDto(Contact contact);

    Contact toEntity(ContactDTO contactDTO);

    List<ContactDTO> toDtoList(List<Contact> contacts);

    List<Contact> toEntityList(List<ContactDTO> contactsDto);
}
