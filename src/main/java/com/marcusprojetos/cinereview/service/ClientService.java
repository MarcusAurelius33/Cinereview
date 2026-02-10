package com.marcusprojetos.cinereview.service;

import com.marcusprojetos.cinereview.entities.Client;
import com.marcusprojetos.cinereview.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}

