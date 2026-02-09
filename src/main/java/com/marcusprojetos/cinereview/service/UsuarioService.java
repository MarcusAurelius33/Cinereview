package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.repository.UsuarioRepository;
import com.marcusprojetos.cinereview.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioValidator usuarioValidator;
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        var senha = usuario.getSenha();
        usuarioValidator.validarUsuario(usuario);
        usuario.setSenha(passwordEncoder.encode(senha));

        repository.save(usuario);
    }

    public Usuario findByLogin(String login){
        return repository.findByLogin(login);
    }

    public Usuario obterPorEmail(String email){
        return repository.findByEmail(email);
    }

}
