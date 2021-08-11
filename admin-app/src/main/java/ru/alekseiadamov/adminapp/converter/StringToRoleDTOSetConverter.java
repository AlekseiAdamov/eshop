package ru.alekseiadamov.adminapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.alekseiadamov.db.dto.RoleDTO;

import java.util.HashSet;
import java.util.Set;

@Component
public class StringToRoleDTOSetConverter implements Converter<String, Set<RoleDTO>> {

    @Override
    public Set<RoleDTO> convert(String role) {
        String[] roleAttributes = role.split(";");
        Set<RoleDTO> roleSet = new HashSet<>();
        roleSet.add(new RoleDTO(Long.parseLong(roleAttributes[0]), roleAttributes[1]));
        return roleSet;
    }
}
