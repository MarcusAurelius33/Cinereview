package com.marcusprojetos.cinereview.security;

import com.marcusprojetos.cinereview.entities.Usuario;
import com.marcusprojetos.cinereview.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (deveConverter(authentication)) {
            // extrair o JWT da requisição
            JwtAuthenticationToken jwtAuthToken = (JwtAuthenticationToken) authentication;
            org.springframework.security.oauth2.jwt.Jwt jwt = jwtAuthToken.getToken();

            // dados direto do token
            String login = jwt.getSubject();
            String email = jwt.getClaimAsString("email");
            String idString = jwt.getClaimAsString("id");
            List<String> roles = jwt.getClaimAsStringList("authorities");

            // usuário de contexto em memória
            Usuario usuarioContexto = new Usuario();
            usuarioContexto.setLogin(login);
            usuarioContexto.setEmail(email);

            if (idString != null) {
                usuarioContexto.setId(java.util.UUID.fromString(idString));
            }

            if (roles != null) {
                usuarioContexto.setRoles(roles);
            }

            // substituicao da autenticacao
            CustomAuthentication customAuth = new CustomAuthentication(usuarioContexto);
            SecurityContextHolder.getContext().setAuthentication(customAuth);
        }

        filterChain.doFilter(request, response);
    }

    private boolean deveConverter(Authentication authentication){
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}
