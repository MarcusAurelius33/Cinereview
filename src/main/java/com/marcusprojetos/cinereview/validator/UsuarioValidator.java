package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.LoginEmUsoException;
import com.marcusprojetos.cinereview.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(Usuario usuario){
        if(loginJaUtilizado(usuario)){
            throw new LoginEmUsoException("Escolha outro login!");
        }
    }

    public boolean loginJaUtilizado(Usuario usuario){
        Usuario usuarioAux = usuarioRepository.findByLogin(usuario.getLogin());
        return usuarioAux != null;
    }
}
