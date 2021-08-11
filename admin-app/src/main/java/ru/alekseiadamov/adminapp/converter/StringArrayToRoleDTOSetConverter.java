package ru.alekseiadamov.adminapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.alekseiadamov.db.dto.RoleDTO;

import java.util.HashSet;
import java.util.Set;

@Component
public class StringArrayToRoleDTOSetConverter implements Converter<String[], Set<RoleDTO>> {

    @Override
    public Set<RoleDTO> convert(String[] roles) {
        Set<RoleDTO> roleSet = new HashSet<>();
        for (String role : roles) {
            String[] roleAttributes = role.split(";");
            roleSet.add(new RoleDTO(Long.parseLong(roleAttributes[0]), roleAttributes[1]));
        }
        return roleSet;
    }
}
