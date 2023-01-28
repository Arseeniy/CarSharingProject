package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.dto.ClientDto;
import com.arseeniy.mycarsharing.exception.ClientNotFoundException;
import com.arseeniy.mycarsharing.entity.booking.Client;
import com.arseeniy.mycarsharing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public String createClient(ClientDto clientDto) {

        List<Client> existClientsList = clientRepository.findAll();
        for (Client certainClient : existClientsList) {
            if (certainClient.getUserName().equals(clientDto.getUserName())) {
                return "Пользователь с таким именем уже существует";
            }
        }
        Client newClient = new Client();
        newClient.setFirstName(clientDto.getFirstName());
        newClient.setLastName(clientDto.getLastName());
        newClient.setUserName(clientDto.getUserName());
        newClient.setPassword(clientDto.getPassword());
        clientRepository.save(newClient);

        return "Регистрация прошла успешно!";
    }

    @Override
    public String authorizeClient(String userName, String password) {
        Client clientFromDB;
        try {
            clientFromDB = clientRepository.findByUserName(userName);
        } catch (ClientNotFoundException e) {
            return e.getMessage();
        }
        if (clientFromDB.getPassword().equals(password)) {
            return "Вы успешно авторизованы!";
        } else return "Неверный пароль";
    }
}
