package ru.alekseiadamov.adminapp.service;

import ru.alekseiadamov.db.dto.ProductDTO;
import ru.alekseiadamov.db.dto.ProductListParamsDTO;

public interface ProductService extends EntityRetrieverService<ProductDTO, ProductListParamsDTO>, EntityManipulatorService<ProductDTO> {
}
