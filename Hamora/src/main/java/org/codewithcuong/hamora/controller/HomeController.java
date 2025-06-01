package org.codewithcuong.hamora.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.codewithcuong.hamora.constant.ConstantVariables;
import org.codewithcuong.hamora.model.Hotel;
import org.codewithcuong.hamora.model.Location;
import org.codewithcuong.hamora.model.Review;
import org.codewithcuong.hamora.service.HotelService;
import org.codewithcuong.hamora.service.LocationService;
import org.codewithcuong.hamora.service.ReviewService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    LocationService locationService;

    @Autowired
    HotelService hotelService;

    @Autowired
    ReviewService reviewService;

    @GetMapping({"/", "/home"})
    public String home(Model model, HttpSession session) {
        model.addAttribute(ConstantVariables.PAGE_TITLE, "Hamora Booking");
        List<Location> locations = locationService.getAllLocations();
        session.setAttribute(ConstantVariables.LOCATIONS, locations);


        // Fetch top 4 high-rated hotels and add to model
        List<Hotel> topHotels = hotelService.getTop8HighRatedHotels();
        model.addAttribute("topHotels", topHotels);

        // Fetch top 5 public positive reviews and add to model
        List<Review> top5Reviews = reviewService.getTop5PublicPositiveReviews();
        model.addAttribute("top5Reviews", top5Reviews);


        return "page/homepage";
    }
}
