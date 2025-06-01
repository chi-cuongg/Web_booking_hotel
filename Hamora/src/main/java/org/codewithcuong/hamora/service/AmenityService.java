package org.codewithcuong.hamora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.Amenity;
import org.codewithcuong.hamora.repository.AmenityRepository;

import java.util.List;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public List<Amenity> getAllAmenities() {
        return amenityRepository.getAllAmenities();
    }

    public List<Amenity> getAmenitiesByCategoryId(int categoryId) {
        return amenityRepository.getAmenitiesByCategoryId(categoryId);
    }

    public List<Amenity> getAllAmenitiesWithCategory() {
        return amenityRepository.getAllAmenitiesWithCategory();
    }

}
