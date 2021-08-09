package ru.alekseiadamov.adminapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.alekseiadamov.db.dto.RoleDTO;

@Component
public class RoleDTOToStringConverter implements Converter<RoleDTO, String> {

    @Override
    public String convert(RoleDTO role) {
        return role.getId() + ";" + role.getName();
    }
}
