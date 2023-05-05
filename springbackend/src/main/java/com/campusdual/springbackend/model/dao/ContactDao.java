package com.campusdual.springbackend.model.dao;


import com.campusdual.springbackend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDao extends JpaRepository<Contact, Integer> {
}
