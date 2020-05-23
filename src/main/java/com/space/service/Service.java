package com.space.service;

import com.space.controller.ShipOrder;
import com.space.controller.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired Repository repository;

    public Ship save(Ship ship) {
        return repository.save(ship);
    }

    public List<Ship> listShips(String name,
                              String planet,
                              ShipType shipType,
                              Long after,
                              Long before,
                              Boolean isUsed,
                              Double minSpeed,
                              Double maxSpeed,
                              Integer minCrewSize,
                              Integer maxCrewSize,
                              Double minRating,
                              Double maxRating,
                              ShipOrder order,
                              Integer pageNumber,
                              Integer pageSize) {
        return repository.findAll(ShipSpecifications.getSearchSpecification(name, planet, shipType,
                after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating),
                ShipSpecifications.getPagingAndSortingParameters(order, pageNumber, pageSize)).getContent();
    }

    public Long countShips(String name,
                                String planet,
                                ShipType shipType,
                                Long after,
                                Long before,
                                Boolean isUsed,
                                Double minSpeed,
                                Double maxSpeed,
                                Integer minCrewSize,
                                Integer maxCrewSize,
                                Double minRating,
                                Double maxRating) {
        return repository.count(ShipSpecifications.getSearchSpecification(name, planet, shipType,
                after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating));
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
