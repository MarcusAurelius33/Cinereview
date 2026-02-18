package com.marcusprojetos.cinereview.validator;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.exceptions.CampoInvalidoException;
import com.marcusprojetos.cinereview.exceptions.LoginEmUsoException;
import com.marcusprojetos.cinereview.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(Usuario usuario){
        if(loginJaUtilizado(usuario)){
            throw new LoginEmUsoException("Escolha outro login!");
        }

        if(emailJaUtilizado(usuario)){
            throw new LoginEmUsoException("Email já utilizado!");
        }

        List<String> ROLES_PERMITIDAS = List.of("ADMIN", "USER");

        List<String> rolesEnviadas = usuario.getRoles();

        if (rolesEnviadas == null || rolesEnviadas.isEmpty()) {
            throw new CampoInvalidoException("roles", "O usuário deve ter pelo menos um perfil atribuído.");
        }

        boolean todasRolesSaoValidas = rolesEnviadas.stream()
                .allMatch(ROLES_PERMITIDAS::contains);

        if (!todasRolesSaoValidas) {
            throw new CampoInvalidoException("roles", "Uma ou mais roles informadas são inválidas.");
        }

    }

    public boolean emailJaUtilizado(Usuario usuario){
        Usuario usuarioAux = usuarioRepository.findByEmail(usuario.getEmail());
        return usuarioAux != null;
    }

    public boolean loginJaUtilizado(Usuario usuario){
        Usuario usuarioAux = usuarioRepository.findByLogin(usuario.getLogin());
        return usuarioAux != null;
    }
}
