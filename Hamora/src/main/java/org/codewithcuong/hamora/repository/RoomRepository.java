package org.codewithcuong.hamora.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.codewithcuong.hamora.model.Room;

import java.util.List;

@Repository
public class RoomRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final BeanPropertyRowMapper<Room> ROOM_MAPPER = new BeanPropertyRowMapper<>(Room.class);

    public List<Room> getRoomsByHotelId(int id){
        String query = """
select 
    room_id as roomId,
    hotel_id as hotelId,
    title,
    Rooms.description,
    price,
    max_guests as maxGuest,
    status,
    quantity,
    name as roomType
from Rooms
join RoomTypes on Rooms.room_type_id = RoomTypes.room_type_id
where hotel_id = ?
                """;
        return jdbcTemplate.query(query, ROOM_MAPPER, id);
    }

    public List<String> getRoomImages(int id){
        String query = "select image_url from RoomImages where room_id = ?";
        return jdbcTemplate.queryForList(query, String.class, id);
    }

    public int insertRoom(Room room) {
        String sql = """
            INSERT INTO Rooms (hotel_id, title, description, price, max_guests, room_type_id, quantity, status)
            OUTPUT INSERTED.room_id
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.queryForObject(sql, Integer.class,
                room.getHotelId(),
                room.getTitle(),
                room.getDescription(),
                room.getPrice(),
                room.getMaxGuests(),
                room.getRoomTypeId(),
                room.getQuantity(),
                room.getStatus() != null ? room.getStatus() : "active"
        );
    }

    public void insertRoomImage(int roomId, String imageUrl) {
        String sql = "INSERT INTO RoomImages (room_id, image_url) VALUES (?, ?)";
        jdbcTemplate.update(sql, roomId, imageUrl);
    }

    public void linkRoomAmenity(int roomId, int amenityId) {
        String sql = "INSERT INTO RoomAmenities (room_id, amenity_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, roomId, amenityId);
    }
}
