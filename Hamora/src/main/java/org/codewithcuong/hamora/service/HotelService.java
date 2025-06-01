package org.codewithcuong.hamora.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.Hotel;
import org.codewithcuong.hamora.repository.HotelRepository;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel getHotelById(int id){
        return hotelRepository.getHotelById(id);
    }

    public List<Hotel> getHotelsSortedByRating() {
        return hotelRepository.getHotelsSortedByRating();
    }

    public List<Hotel> getTop8HighRatedHotels() {
        return hotelRepository.getTop8HighRatedHotels();
    }

    public List<Hotel> getHotelsByLocation(int id, String search) {
        if(id == -1) return hotelRepository.searchHotel(search);
        return hotelRepository.getHotelsByLocation(id, search);
    }

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.insertHotel(hotel);
    }

    public List<Hotel> getHotelsByHostId(int hostId) {
        return hotelRepository.findByHostId(hostId);
    }

}
