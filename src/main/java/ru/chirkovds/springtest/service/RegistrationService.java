package ru.chirkovds.springtest.service;

import ru.chirkovds.springtest.entity.DTO.RegistrationDTO;

public interface RegistrationService {
    void saveNewClient(RegistrationDTO registrationDTO);
}