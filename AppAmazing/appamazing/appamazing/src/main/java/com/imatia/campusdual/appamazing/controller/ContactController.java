package com.imatia.campusdual.appamazing.controller;

import com.imatia.campusdual.appamazing.api.IContactService;
import com.imatia.campusdual.appamazing.model.dto.ContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private IContactService contactService;

    @GetMapping(value = "/get")
    public ContactDTO getContact(@RequestBody ContactDTO contactDTO){
        return contactService.querycontact(contactDTO);
    }

    @GetMapping(value = "/getAll")
    public List<ContactDTO> getAllContacts(){
        return contactService.queryAll();
    }

    @PostMapping(value = "/add")
    public int addContact(@RequestBody ContactDTO contactDTO){
        return contactService.insertContact(contactDTO);
    }

    @PutMapping(value = "/update")
    public int updateContact(@RequestBody ContactDTO contactDTO){
        if(contactService.querycontact(contactDTO) != null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "HTTP Status will be NOT FOUND (CODE 404)\n");
        }
        return contactService.updateContact(contactDTO);
    }

    @DeleteMapping(value = "/delete")
    public int deleteContact(@RequestBody ContactDTO contactDTO){
        return contactService.deleteContact(contactDTO);
    }
    
}
