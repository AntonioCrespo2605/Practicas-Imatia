package com.imatia.campusdual.appamazing.model.dao;

import com.imatia.campusdual.appamazing.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDAO extends JpaRepository<Contact, Integer> {

}
