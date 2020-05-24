package com.space.service;

import com.space.controller.ShipOrder;
import com.space.service.exceptions.InvalidInputException;
import com.space.service.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.model.ValuesHelper;
import com.space.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
        Ship shipToUpdate = repository.findById(id).orElseThrow(NotFoundException::new);
        if(ship.getName() != null) shipToUpdate.setName(ship.getName());
        if(ship.getPlanet() != null) shipToUpdate.setPlanet(ship.getPlanet());
        if(ship.getShipType() != null) shipToUpdate.setShipType(ship.getShipType());
        if(ship.getProdDate() != null) shipToUpdate.setProdDate(ship.getProdDate());
        if(ship.getUsed() != null) shipToUpdate.setUsed(ship.getUsed());
        if(ship.getSpeed() != null) shipToUpdate.setSpeed(ValuesHelper.roundToHundredths(ship.getSpeed()));
        if(ship.getCrewSize() != null) shipToUpdate.setCrewSize(ship.getCrewSize());
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

    private Ship validateAndGetShip(Ship ship) {
        String name = ship.getName();
        String planet = ship.getPlanet();
        Double speed = ship.getSpeed();
        Integer crewSize = ship.getCrewSize();
        Date prodDate;
        if((prodDate = ship.getProdDate()) == null) {
            throw new InvalidInputException();
        }
        Calendar prodDateCalendar = Calendar.getInstance();
        prodDateCalendar.setTime(prodDate);
        if (    name == null ||
                name.equals("") ||
                name.length() > ValuesHelper.MAX_NAME_LENGTH ||
                planet == null ||
                planet.equals("") ||
                planet.length() > ValuesHelper.MAX_PLANET_LENGTH ||
                prodDateCalendar.get(Calendar.YEAR) < ValuesHelper.START_YEAR ||
                prodDateCalendar.get(Calendar.YEAR) > ValuesHelper.CURRENT_YEAR ||
                speed == null ||
                speed < ValuesHelper.MIN_SPEED ||
                speed > ValuesHelper.MAX_SPEED ||
                crewSize == null ||
                crewSize < ValuesHelper.MIN_CREW_SIZE ||
                crewSize > ValuesHelper.MAX_CREW_SIZE) {
            throw new InvalidInputException();
        }
        if (ship.getUsed() == null) {
            ship.setUsed(false);
        }
        ship.setSpeed(ValuesHelper.roundToHundredths(speed)); // setting speed value rounded to hundredths
        ship.setRating(ship.getCalculatedRating()); // setting the calculated rating
        return ship;
    }

}
