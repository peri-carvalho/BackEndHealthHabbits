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
    public UserDTO createUser(@Valid UserDTO userDTO) {
        // Verificar se já existe um usuário com o mesmo email
        if (this.userAccountRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlreadyExists("Já existe um usuário com esse Email.");
        }
        // Buscar o perfil no banco de dados
        Profile profile = this.profileRepository.findById(userDTO.getProfileId())
                .orElseThrow(() -> new NotFound("Perfil não encontrado"));

        // Criptografar a senha
        String encryptedPassword = this.passwordEncoder.encode(userDTO.getPassword());

        // Criar o novo usuário passando email e perfil na ordem correta
        User user = new User(userDTO.getEmail(), encryptedPassword, profile);

        this.userAccountRepository.save(user);
        // Salvar o usuário no banco de dados
        return new UserDTO(user);
    }
}
