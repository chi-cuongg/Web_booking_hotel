package org.codewithcuong.hamora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amenity {
    private int amenityId;
    private String name;
    private int categoryId;

    private int roomId;
    private AmenityCategory category;  // Optional for JOIN
}
