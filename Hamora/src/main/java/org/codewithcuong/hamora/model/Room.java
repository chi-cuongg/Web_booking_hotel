package org.codewithcuong.hamora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    private int roomId;
    private int hotelId;
    private String title;
    private String description;
    private float price;
    private int maxGuests;
    private String status;
    private int quantity;
    private int roomTypeId;

    private String roomType;

    private List<String> images;
}
