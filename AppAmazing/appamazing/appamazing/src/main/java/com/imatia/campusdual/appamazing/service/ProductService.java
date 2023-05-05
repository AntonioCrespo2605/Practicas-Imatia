package com.imatia.campusdual.appamazing.service;

import com.imatia.campusdual.appamazing.api.IProductService;
import com.imatia.campusdual.appamazing.model.Product;
import com.imatia.campusdual.appamazing.model.dao.ProductDAO;
import com.imatia.campusdual.appamazing.model.dto.ProductDTO;
import com.imatia.campusdual.appamazing.model.dto.dtomapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductService")
@Lazy
public class ProductService implements IProductService {

    @Autowired
    private ProductDAO productDAO;

    @Override
    public ProductDTO queryProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        return ProductMapper.INSTANCE.toDto(productDAO.getReferenceById(product.getId()));
    }

    @Override
    public List<ProductDTO> queryAll() {
        return ProductMapper.INSTANCE.toDtoList(productDAO.findAll());
    }

    @Override
    public int insertProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        productDAO.saveAndFlush(product);
        return product.getId();
    }

    @Override
    public int updateProduct(ProductDTO productDTO) {
        return insertProduct(productDTO);
    }

    @Override
    public int deleteProduct(ProductDTO productDTO) {
        Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        productDAO.delete(product);
        return productDTO.getId();
    }
}
