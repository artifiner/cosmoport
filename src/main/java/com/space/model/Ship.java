package com.space.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;            // ID корабля
    String name;        // Название корабля (до 50 знаков включительно)
    String planet;      // Планета пребывания (до 50 знаков включительно)
    ShipType shipType;  // Тип корабля
    Date prodDate;      // Дата выпуска. Диапазон значений года 2800..3019 включительно
    Boolean isUsed;     // Использованный / новый
    Double speed;       // Максимальная скорость корабля. Диапазон значений 0,01..0,99 включительно.
                        // Используй математическое округление до сотых.
    Integer crewSize;   // Количество членов экипажа. Диапазон значений 1..9999 включительно.
    Double rating;      // Рейтинг корабля. Используй математическое округление до сотых.

    public Ship() {
    }

    public Ship(String name, String planet, ShipType shipType, Date prodDate, Boolean isUsed, Double speed, Integer crewSize) {
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        refreshRating();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        refreshRating();
        return rating;
    }

    private void refreshRating() {
        rating = 80 * speed * (isUsed ? 0.5 : 1) / (3019 - prodDate.getYear() + 1); //TODO: Change 3019 into current year call
    }
}
