package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Client;
import com.marcusprojetos.cinereview.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;

    public Client salvar(Client client){
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        return clientRepository.save(client);
    }

    public Client obterPorClientID(String clientId){
        return clientRepository.findByClientId(clientId);
    }

    // Em ClientService.java

    public Client obterPorId(String id) {
        // Converte a String para UUID e busca no banco
        return clientRepository.findById(UUID.fromString(id)).orElse(null);
    }
}

