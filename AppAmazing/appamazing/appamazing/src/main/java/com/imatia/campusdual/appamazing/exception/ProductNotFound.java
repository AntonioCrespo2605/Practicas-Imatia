package com.imatia.campusdual.appamazing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFound extends ResponseStatusException {
    public ProductNotFound(String message){
        super(HttpStatus.NOT_FOUND,message);
    }
}
