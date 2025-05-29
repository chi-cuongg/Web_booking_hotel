package org.codewithcuong.hamora.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.codewithcuong.hamora.model.Hotel;
import org.codewithcuong.hamora.model.Location;
import org.codewithcuong.hamora.service.HotelService;
import org.codewithcuong.hamora.service.LocationService;

@Controller
public class HotelController {
    @Autowired
    HotelService hotelService;

    @Autowired
    LocationService locationService;

    @GetMapping("/hotel-list")
    public String hotelList(@RequestParam(value = "locationId", defaultValue = "-1") int locationId, @RequestParam(value = "search", defaultValue = "") String search, Model model){
        List<Location> location = locationService.getLocationById(locationId);
        model.addAttribute("hamora", locationId == -1 ? new Location(locationId, "Hamora", "assets/images/bg/05.jpg") : location.get(0));
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);

        List<Hotel> hotel = hotelService.getHotelsByLocation(locationId, search);
        model.addAttribute("hotels", hotel);

        return "page/hotelList";
    }
}
