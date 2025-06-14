package org.codewithcuong.hamora.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.codewithcuong.hamora.model.Hotel;
import org.codewithcuong.hamora.model.Location;
import org.codewithcuong.hamora.model.Room;
import org.codewithcuong.hamora.service.HotelService;
import org.codewithcuong.hamora.service.LocationService;
import org.codewithcuong.hamora.service.RoomService;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class HotelDetailController {
    @Autowired
    LocationService locationService;
    @Autowired
    HotelService hotelService;
    @Autowired
    RoomService roomService;

    @GetMapping("/hotel-detail")
    public String hotelDetail(@RequestParam(value = "hotelId") int hotelId, Model model){
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);

        Hotel hotel = hotelService.getHotelById(hotelId);
        if (hotel.getPolicy() != null) {
            hotel.setPolicy(hotel.getPolicy().replace(
                    "<li>",
                    "<li class=\"list-group-item d-flex\"><i class=\\\"bi bi-arrow-right me-2\\\"></i>"
            ));
        } else {
            hotel.setPolicy("");
        }
        model.addAttribute("hotel", hotel);

        String description = hotel.getDescription();

        if (description != null) {
            int index = description.indexOf("<br><br><b>");
            if (index != -1) {
                model.addAttribute("short", description.substring(0, index));
                model.addAttribute("long", description.substring(index));
            } else {
                if (description.length() > 800) {
                    model.addAttribute("short", description.substring(0, 800));
                    model.addAttribute("long", description.substring(800));
                } else {
                    model.addAttribute("short", description);
                    model.addAttribute("long", "");
                }
            }
        } else {
            model.addAttribute("short", "");
            model.addAttribute("long", "");
        }


        List<Room> rooms = roomService.getRoomByHotelId(hotelId);
        model.addAttribute("rooms", rooms);
        
        String hotelName = hotel.getHotelName();
        String encode = URLEncoder.encode(hotelName, StandardCharsets.UTF_8);
        String map = "https://www.google.com/maps/search/" + encode + "//@" + hotel.getLatitude() + "," + hotel.getLongitude() + ",17z";
        model.addAttribute("map", map);

        return "page/hotelDetail";
    }    
}
