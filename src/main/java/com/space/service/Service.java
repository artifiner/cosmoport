package com.space.service;

import com.space.controller.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired Repository repository;

    public void save(Ship ship) {
        repository.save(ship);
    }

    public List<Ship> listAll() {
        return (List<Ship>) repository.findAll();
    }

    public Ship get(Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }
}
