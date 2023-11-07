package com.main.travel.entities;

import jakarta.persistence.*;
@Entity
@Table(name = "Trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int id;

    @Column(name = "price_per_week")
    private double pricePerWeek;
    @Column(name = "hotel")
    private String hotel;

    @OneToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public Trip() {
    }

    public Trip(int id, double pricePerWeek, String hotel, Destination destination) {
        this.id = id;
        this.pricePerWeek = pricePerWeek;
        this.hotel = hotel;
        this.destination = destination;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public double getPricePerWeek() {return pricePerWeek;}

    public void setPricePerWeek(double pricePerWeek) {this.pricePerWeek = pricePerWeek;}

    public String getHotel() {return hotel;}

    public void setHotel(String hotel) {this.hotel = hotel;}

    public Destination getDestination() {return destination;}

    public void setDestination(Destination destination) {this.destination = destination;}

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", pricePerWeek=" + pricePerWeek +
                ", hotel='" + hotel + '\'' +
                ", destination=" + destination +
                '}';
    }
}
