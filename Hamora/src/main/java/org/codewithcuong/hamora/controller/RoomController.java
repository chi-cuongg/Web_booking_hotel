package org.codewithcuong.hamora.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {
    @GetMapping("/room-list")
    public String roomList(){
        return "roomList";
    }
}
