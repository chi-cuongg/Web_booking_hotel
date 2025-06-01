package org.codewithcuong.hamora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.RoomTypes;
import org.codewithcuong.hamora.repository.RoomTypeRepository;

import java.util.List;

@Service
public class RoomTypeService {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<RoomTypes> getAllRoomTypes() {
        return roomTypeRepository.getAllRoomTypes();
    }

    public RoomTypes getRoomTypeById(int roomTypeId) {
        return roomTypeRepository.getRoomTypeById(roomTypeId);
    }
}