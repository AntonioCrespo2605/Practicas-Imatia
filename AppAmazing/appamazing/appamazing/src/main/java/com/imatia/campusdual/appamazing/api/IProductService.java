package com.imatia.campusdual.appamazing.api;

import com.imatia.campusdual.appamazing.model.dto.ProductDTO;

import java.util.List;

public interface IProductService {

    ProductDTO queryProduct(ProductDTO productDTO);

    List<ProductDTO> queryAll();

    int insertProduct(ProductDTO productDTO);

    int updateProduct(ProductDTO productDTO);

    int deleteProduct(ProductDTO productDTO);
}
