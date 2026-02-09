package com.marcusprojetos.cinereview.security;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       if (authentication instanceof CustomAuthentication customAuth){
           return customAuth.getUsuario();
       }
       return null;
    }
}
