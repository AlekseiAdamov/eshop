package ru.alekseiadamov.adminapp.service;

import ru.alekseiadamov.db.dto.CategoryDTO;
import ru.alekseiadamov.db.dto.CategoryParamsDTO;

public interface CategoryService extends EntityRetrieverService<CategoryDTO, CategoryParamsDTO>, EntityManipulatorService<CategoryDTO> {
}
