package com.imatia.campusdual.appamazing.model.dto.dtomapper;

import com.imatia.campusdual.appamazing.model.Product;
import com.imatia.campusdual.appamazing.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toDtoList(List<Product> products);

    List<Product> toEntityList(List<ProductDTO> productsDto);

}
