package com.imatia.campusdual.appamazing.controller;

import com.imatia.campusdual.appamazing.api.IProductService;
import com.imatia.campusdual.appamazing.exception.ProductNotFound;
import com.imatia.campusdual.appamazing.model.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/get")
    public ProductDTO getProduct(@RequestBody ProductDTO productDTO){
        try {
            return productService.queryProduct(productDTO);
        }catch (Exception e){
            throw new ProductNotFound("Product not found");
        }

    }

    @GetMapping(value = "/getAll")
    public List<ProductDTO> getAllProducts(){
        return productService.queryAll();
    }

    @PostMapping(value = "/add")
    public int addProduct(@RequestBody ProductDTO productDTO){
        return productService.insertProduct(productDTO);
    }

    @PutMapping(value = "/update")
    public int updateProduct(@RequestBody ProductDTO productDTO){
        try{
            productService.queryProduct(productDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping(value = "/delete")
    public int deleteProduct(@RequestBody ProductDTO productDTO){
        try{
            productService.queryProduct(productDTO);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return productService.deleteProduct(productDTO);
    }



    /*
    @GetMapping
    public String testController(){
        return "Products controller works!";
    }

    @PostMapping
    public String testController(@RequestBody String name){
        return "Products controller works! " + name;
    }*/
}
