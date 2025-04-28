package com.BackEnd.BackEndHealthHabbits.dto;

import com.BackEnd.BackEndHealthHabbits.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
        @NotBlank(message = "o campo nome não pode estar vazio")
        @Email
        private String email;

        private String name;

        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-]{1,8}$", message = "Senha deve conter 8 digitos com um caractere especial.")
        private String password;

        @JsonProperty(access = JsonProperty.Access.READ_WRITE)
        @Pattern(regexp = "^(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;\"'<>?,./\\\\|-]{1,8}$", message = "Senha Inválida")
        private String confirmPassword;

        @NotNull(message = "Perfil não pode ser nulo")
        private Long profileId;

        public UserDTO(User entity) {
                this.email = entity.getEmail();
                this.name = entity.getName();
                this.password = entity.getPassword();
                this.profileId = entity.getProfile().getId();
        }
}
