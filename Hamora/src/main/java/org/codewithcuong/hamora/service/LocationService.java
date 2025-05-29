package org.codewithcuong.hamora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.codewithcuong.hamora.model.Location;
import org.codewithcuong.hamora.repository.LocationRepository;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.getAllLocations();
    }

    public List<Location> getLocationById(int locationId) {
        if(locationId == -1) return locationRepository.getAllLocations();
        return locationRepository.getLocation(locationId);
    }
}
