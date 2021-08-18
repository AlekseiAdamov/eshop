package ru.alekseiadamov.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatedPassword;

    private Set<RoleDTO> roles;

    public UserDTO(Long id, String name, Set<RoleDTO> roles) {
        this.id = id;
        this.name = name;
        this.roles = new HashSet<>(roles);
    }
}
