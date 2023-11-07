package com.main.travel.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int id;
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @Column(name = "date_Of_Departure")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfDeparture;
    @Column(name = "date_Of_Return")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReturn;
    @Column(name = "SEK")
    private double SEK;
    @Column(name = "PLN")
    private double PLN;

    public Booking() {}

    public Booking(int id, Trip trip, Date dateOfDeparture, Date dateOfReturn, double SEK, double PLN) {
        this.id = id;
        this.trip = trip;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfReturn = dateOfReturn;
        this.SEK = SEK;
        this.PLN = PLN;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Trip getTrip() {return trip;}

    public void setTrip(Trip trip) {this.trip = trip;}

    public Date getDateOfDeparture() {return dateOfDeparture;}

    public void setDateOfDeparture(Date dateOfDeparture) {this.dateOfDeparture = dateOfDeparture;}

    public Date getDateOfReturn() {return dateOfReturn;}

    public void setDateOfReturn(Date dateOfReturn) {this.dateOfReturn = dateOfReturn;}

    public double getSEK() {return SEK;}

    public void setSEK(double SEK) {this.SEK = SEK;}

    public double getPLN() {return PLN;}

    public void setPLN(double PLN) {this.PLN = PLN;}


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", trip=" + trip +
                ", dateOfDeparture=" + dateOfDeparture +
                ", dateOfReturn=" + dateOfReturn +
                ", SEK=" + SEK +
                ", PLN=" + PLN +
                '}';
    }

    //CALCULATE THE TOTAL AMOUNT OF THE BOOKING AND RETURN IT
    public double calculateTotalAmount(Trip trip) {
        if (trip != null) {
            double pricePerWeek = trip.getPricePerWeek();
            long duration = ChronoUnit.DAYS.between(dateOfDeparture.toInstant(), dateOfReturn.toInstant()) + 1;
            System.out.println("Calculating: ("+pricePerWeek+"/7) * " + duration);
            SEK = (pricePerWeek / 7) * duration;
            System.out.println("Total: ("+SEK+")");
        }
        return SEK;
    }
}
