package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.UserDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("RESOURCE NOT FOUND!"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(x -> new UserDTO(x));
    }

    @Transactional
    public UserDTO insert(UserDTO userDTO) {
        User entity = new User();
        copyDtoToEntity(userDTO, entity);
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        try {
            User entity = userRepository.getReferenceById(id);
            copyDtoToEntity(userDTO, entity);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ENTITY NOT FOUND CANNOT UPDATE");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        }
        catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("RESOURCE NOT FOUND !");
        }
    }

    private void copyDtoToEntity(UserDTO userDTO, User entity) {
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setEmail(userDTO.getEmail());
        entity.setPassword(userDTO.getPassword());
    }

}
