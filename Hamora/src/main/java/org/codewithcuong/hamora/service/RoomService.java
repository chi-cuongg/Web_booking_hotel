package org.codewithcuong.hamora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.Room;
import org.codewithcuong.hamora.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getRoomByHotelId(int id){
        List<Room> rooms = roomRepository.getRoomsByHotelId(id);

        for(Room room : rooms){
            room.setImages(roomRepository.getRoomImages(room.getRoomId()));
        }

        return rooms;
    }

    public void saveRoom(Room room, List<Integer> amenityIds, List<String> imageUrls) {
        // Step 1: Save the room and get its generated room_id
        int roomId = roomRepository.insertRoom(room);

        // Step 2: Save room images
        if (imageUrls != null) {
            for (String url : imageUrls) {
                roomRepository.insertRoomImage(roomId, url);
            }
        }

        //  Link room to amenities
        if (amenityIds != null) {
            for (Integer amenityId : amenityIds) {
                roomRepository.linkRoomAmenity(roomId, amenityId);
            }
        }
    }

    /**
     * Returns all rooms associated with a given hotel.
     */
    public List<Room> getRoomsByHotelId(int hotelId) {
        return roomRepository.getRoomsByHotelId(hotelId);
    }
}
