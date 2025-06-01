package org.codewithcuong.hamora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    private int hotelId;
    private int hostId;
    private String hotelName;
    private String address;
    private String description;
    private int locationId;
    private String hotelImageUrl;
    private double rating;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String policy;

    private String cityName; // Added field for city name
    private BigDecimal minPrice; // For the minimum room price

}
