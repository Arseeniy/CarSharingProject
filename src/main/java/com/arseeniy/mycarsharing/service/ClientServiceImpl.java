package com.arseeniy.mycarsharing.service;

import com.arseeniy.mycarsharing.ClientNotFoundException;
import com.arseeniy.mycarsharing.NoSuchElementFoundException;
import com.arseeniy.mycarsharing.entity.Client;
import com.arseeniy.mycarsharing.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public String createClient(String firstName, String lastName, String userName, String password) {

        List<Client> existClientsList = clientRepository.findAll();
        for (Client certainClient : existClientsList) {
            if (certainClient.getUserName().equals(userName)) {
                return "Пользователь с таким именем уже существует";
            }
        }
        Client newClient = new Client();
        newClient.setFirstName(firstName);
        newClient.setLastName(lastName);
        newClient.setUserName(userName);
        newClient.setPassword(password);
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
