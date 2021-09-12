package ru.alekseiadamov.adminapp.service;

import ru.alekseiadamov.db.dto.UserDTO;
import ru.alekseiadamov.db.dto.UserListParamsDTO;

public interface UserService extends EntityRetrieverService<UserDTO, UserListParamsDTO>, EntityManipulatorService<UserDTO> {
}
