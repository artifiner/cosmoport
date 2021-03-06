package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/ships")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping
    public ResponseEntity<List<Ship>> getShipList(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String planet,
                                  @RequestParam(required = false) ShipType shipType,
                                  @RequestParam(required = false) Long after,
                                  @RequestParam(required = false) Long before,
                                  @RequestParam(required = false) Boolean isUsed,
                                  @RequestParam(required = false) Double minSpeed,
                                  @RequestParam(required = false) Double maxSpeed,
                                  @RequestParam(required = false) Integer minCrewSize,
                                  @RequestParam(required = false) Integer maxCrewSize,
                                  @RequestParam(required = false) Double minRating,
                                  @RequestParam(required = false) Double maxRating,
                                  @RequestParam(required = false, defaultValue = "ID") ShipOrder order,
                                  @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                  @RequestParam(required = false, defaultValue = "3") Integer pageSize) {
        List<Ship> list = service.listShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getShipCount(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String planet,
                                             @RequestParam(required = false) ShipType shipType,
                                             @RequestParam(required = false) Long after,
                                             @RequestParam(required = false) Long before,
                                             @RequestParam(required = false) Boolean isUsed,
                                             @RequestParam(required = false) Double minSpeed,
                                             @RequestParam(required = false) Double maxSpeed,
                                             @RequestParam(required = false) Integer minCrewSize,
                                             @RequestParam(required = false) Integer maxCrewSize,
                                             @RequestParam(required = false) Double minRating,
                                             @RequestParam(required = false) Double maxRating) {
        Long count = service.countShips(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating);
        if(count == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Ship> getShip(@PathVariable Long id) {
        if (id <=0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ship> create(@RequestBody Ship ship) {
        Ship createdShip = service.save(ship);
        if(createdShip == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdShip, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<Ship> update(@PathVariable Long id, @RequestBody Ship ship) {
        if(id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship updatedShip = service.update(id, ship);
        if(updatedShip == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedShip, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (id <=0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
