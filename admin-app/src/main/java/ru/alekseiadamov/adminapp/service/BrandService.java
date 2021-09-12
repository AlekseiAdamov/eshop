package ru.alekseiadamov.adminapp.service;

import ru.alekseiadamov.db.dto.BrandDTO;
import ru.alekseiadamov.db.dto.BrandParamsDTO;

public interface BrandService extends EntityRetrieverService<BrandDTO, BrandParamsDTO>, EntityManipulatorService<BrandDTO> {
}
