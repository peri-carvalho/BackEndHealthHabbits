package com.BackEnd.BackEndHealthHabbits.services;

import com.BackEnd.BackEndHealthHabbits.dto.UserDTO;
import com.BackEnd.BackEndHealthHabbits.entities.Profile;
import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.BackEnd.BackEndHealthHabbits.infra.exceptions.AlreadyExists;
import com.BackEnd.BackEndHealthHabbits.infra.exceptions.NotFound;
import com.BackEnd.BackEndHealthHabbits.repositories.ProfileRepository;
import com.BackEnd.BackEndHealthHabbits.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountService {

    @Autowired
    private UserRepository userAccountRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(@Valid UserDTO userDTO) {
        // Verificar se já existe um usuário com o mesmo username
        if (userAccountRepository.existsByName(userDTO.username())) {
            throw new AlreadyExists("Já existe um usuário com esse CPF.");
        }
        // Buscar o perfil no banco de dados
        Profile profile = profileRepository.findById(userDTO.profileId())
                .orElseThrow(() -> new NotFound("Perfil não encontrado"));

        // Criptografar a senha
        String encryptedPassword = passwordEncoder.encode(userDTO.password());

        // Criar o novo usuário passando username e perfil na ordem correta
        User user = new User(userDTO.username(), encryptedPassword, profile);

        // Salvar o usuário no banco de dados
        return userAccountRepository.save(user);
    }
}
