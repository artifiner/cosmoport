package com.space.controller;

import com.space.model.Ship;
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
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getShipList(@RequestParam String name ) {
        return null;
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Ship> getShip(@PathVariable("id") String id) {
        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        if (parsedId <=0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.get(parsedId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Long create(@RequestBody Ship ship) {
        return null;

    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Ship update(@PathVariable Long id, @RequestBody Ship ship) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        long parsedId;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        if (parsedId <=0) {
            return ResponseEntity.badRequest().build();
        }
        service.delete(parsedId);
        return ResponseEntity.ok().build();
    }

}
