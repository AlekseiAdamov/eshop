package ru.alekseiadamov.adminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alekseiadamov.db.dao.UserRepository;
import ru.alekseiadamov.db.dto.RoleDTO;
import ru.alekseiadamov.db.dto.UserDTO;
import ru.alekseiadamov.db.dto.UserListParamsDTO;
import ru.alekseiadamov.db.entity.Role;
import ru.alekseiadamov.db.entity.User;
import ru.alekseiadamov.db.entity.UserSpecification;
import ru.alekseiadamov.db.util.PageParametersProcessor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        mapRolesToRolesDTO(user)))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return repository.findById(id).map(user -> new UserDTO(
                user.getId(),
                user.getUsername(),
                mapRolesToRolesDTO(user)));
    }

    @Override
    public Page<UserDTO> findWithFilter(UserListParamsDTO params) {
        Specification<User> specification = Specification.where(null);
        if (params.getName() != null && !params.getName().isBlank()) {
            specification = specification.and(UserSpecification.username(params.getName()));
        }

        return repository.findAll(specification, PageParametersProcessor.getPageRequest(params))
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        mapRolesToRolesDTO(user)));
    }

    @Override
    public UserDTO getById(Long id) {
        User user = repository.getById(id);
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                mapRolesToRolesDTO(user));
    }

    private Set<RoleDTO> mapRolesToRolesDTO(User user) {
        return user.getRoles()
                .stream()
                .map(role -> new RoleDTO(
                        role.getId(),
                        role.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void save(UserDTO user) {
        Set<RoleDTO> roles = user.getRoles();
        if (roles == null) {
            roles = new HashSet<>();
        }
        User persistentUser = new User(
                user.getId(),
                user.getName(),
                passwordEncoder.encode(user.getPassword()),
                roles.stream()
                        .map(role -> new Role(
                                role.getId(),
                                role.getName()))
                        .collect(Collectors.toSet()));
        repository.save(persistentUser);
    }

    @Override
    public Optional<UserDTO> findByName(String username) {
        return repository.findByUsername(username)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        mapRolesToRolesDTO(user)));
    }
}
