package com.marcusprojetos.cinereview.security;

import com.marcusprojetos.cinereview.entities.Client;
import com.marcusprojetos.cinereview.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService clientService;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        var client = clientService.obterPorId(id);
        return client != null ? toRegisteredClient(client) : null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientService.obterPorClientID(clientId);

        if (client == null) {
            System.out.println("DEBUG: Cliente não encontrado no banco: " + clientId);
            return null;
        }

        System.out.println("DEBUG: Cliente carregado: " + client.getClientId());
        System.out.println("DEBUG: Redirect URI do banco: [" + client.getRedirectURI() + "]");
        System.out.println("DEBUG: Scope do banco: [" + client.getScope() + "]");

        return toRegisteredClient(client);
    }

    private RegisteredClient toRegisteredClient(Client client) {
        String redirectUri = client.getRedirectURI().trim();
        String scope = client.getScope().trim();

        return RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId().trim())
                .clientSecret(client.getClientSecret())

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)

                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)

                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri(redirectUri)
                .scope(scope)
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)
                .build();
    }
}
