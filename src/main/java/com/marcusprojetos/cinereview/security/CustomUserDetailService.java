package com.marcusprojetos.cinereview.security;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = service.findByLogin(login);

        if(usuario == null){
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
                .build();
    }
}
