package ru.chirkovds.springtest.service;

import ru.chirkovds.springtest.entity.Spare;

public interface SpareService {
    void deleteSpare (Long id);
    Spare saveSpare (Spare spare);
}
