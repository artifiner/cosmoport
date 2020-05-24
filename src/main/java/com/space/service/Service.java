package com.space.service;

import com.space.controller.ShipOrder;
import com.space.controller.exceptions.InvalidInputException;
import com.space.controller.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.model.ValuesConstraints;
import com.space.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {
    @Autowired Repository repository;

    public Ship save(Ship ship) {
        return repository.save(validateAndGetShip(ship));
    }

    public Ship update(Long id, Ship ship) {
        if(id <= 0) {
            throw new InvalidInputException();
        }
        Ship shipToUpdate = repository.findById(id).orElseThrow(NotFoundException::new);
        if(ship.getName() != null) shipToUpdate.setName(ship.getName());
        if(ship.getPlanet() != null) shipToUpdate.setPlanet(ship.getPlanet());
        if(ship.getShipType() != null) shipToUpdate.setShipType(ship.getShipType());
        if(ship.getProdDate() != null) shipToUpdate.setProdDate(ship.getProdDate());
        if(ship.getUsed() != null) shipToUpdate.setUsed(ship.getUsed());
        if(ship.getSpeed() != null) shipToUpdate.setSpeed(ship.getSpeed());
        if(ship.getCrewSize() != null) shipToUpdate.setCrewSize(ship.getCrewSize());
        shipToUpdate.setRating(shipToUpdate.calculateRating());
        return repository.save(validateAndGetShip(shipToUpdate));
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

    public Ship validateAndGetShip(Ship ship) {
        String name = ship.getName();
        String planet = ship.getPlanet();
        Double speed = ship.getSpeed();
        Integer crewSize = ship.getCrewSize();
        Date prodDate;
        if((prodDate = ship.getProdDate()) == null) {
            throw new InvalidInputException();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(prodDate);
        if (name == null || name.equals("") || name.length() > ValuesConstraints.MAX_NAME_LENGTH ||
                planet == null || planet.equals("") || planet.length() > ValuesConstraints.MAX_PLANET_LENGTH ||
                calendar.get(Calendar.YEAR) < ValuesConstraints.START_YEAR || calendar.get(Calendar.YEAR) > ValuesConstraints.CURRENT_YEAR ||
                speed == null || speed < ValuesConstraints.MIN_SPEED || speed > ValuesConstraints.MAX_SPEED ||
                crewSize == null || crewSize < ValuesConstraints.MIN_CREW_SIZE || crewSize > ValuesConstraints.MAX_CREW_SIZE) {
            throw new InvalidInputException();
        }
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        ship.setRating(ship.calculateRating());
        return ship;
    }

}
