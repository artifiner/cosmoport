package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ShipSpecifications {
    public static Specification<Ship> nameContains(String nameContains) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + nameContains + "%"));
    }

    public static Specification<Ship> planetContains(String planetContains) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("planet"), "%" + planetContains + "%"));
    }

    public static Specification<Ship> shipType(ShipType type) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("shipType"), type));
    }

    public static Specification<Ship> prodDateAfter(Long after) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after)));
    }

    public static Specification<Ship> prodDateBefore(Long before) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("prodDate"), new Date(before)));
    }

    public static Specification<Ship> isUsed(Boolean isUsed) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isUsed"), isUsed));
    }

    public static Specification<Ship> minSpeed(Double minSpeed) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("speed"), minSpeed));
    }

    public static Specification<Ship> maxSpeed(Double maxSpeed) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("speed"), maxSpeed));
    }
    public static Specification<Ship> minCrewSize(Integer minCrewSize) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize));
    }

    public static Specification<Ship> maxCrewSize(Integer maxCrewSize) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize));
    }
    public static Specification<Ship> minRating(Double minRating) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
    }

    public static Specification<Ship> maxRating(Double maxRating) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
    }

    public static Pageable getPagingAndSortingParameters(ShipOrder order, Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
    }

    public static Specification<Ship> getSearchSpecification(String name,
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
        Specification<Ship> spec = null;
        if(name != null) {
            Specification<Ship> nextSpec = nameContains(name);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(planet != null) {
            Specification<Ship> nextSpec = planetContains(planet);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(shipType != null) {
            Specification<Ship> nextSpec = shipType(shipType);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(after != null) {
            Specification<Ship> nextSpec = prodDateAfter(after);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(before != null) {
            Specification<Ship> nextSpec = prodDateBefore(before);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(isUsed != null) {
            Specification<Ship> nextSpec = isUsed(isUsed);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(minSpeed != null) {
            Specification<Ship> nextSpec = minSpeed(minSpeed);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(maxSpeed != null) {
            Specification<Ship> nextSpec = maxSpeed(maxSpeed);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(minCrewSize != null) {
            Specification<Ship> nextSpec = minCrewSize(minCrewSize);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(maxCrewSize != null) {
            Specification<Ship> nextSpec = maxCrewSize(maxCrewSize);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(minRating != null) {
            Specification<Ship> nextSpec = minRating(minRating);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        if(maxRating != null) {
            Specification<Ship> nextSpec = maxRating(maxRating);
            spec = (spec == null) ? nextSpec : spec.and(nextSpec);
        }
        return spec;
    }
}
