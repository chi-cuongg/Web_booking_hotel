package org.codewithcuong.hamora.controller;

import org.codewithcuong.hamora.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DialogflowWebhookController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/webhook")
    public Map<String, Object> handleDialogflow(@RequestBody Map<String, Object> payload) {
        try {
            String userMessage = ((Map<String, Object>) payload.get("queryResult")).get("queryText").toString();
            String response = geminiService.generateReply(userMessage);
            return Map.of("fulfillmentText", response);
        } catch (Exception e) {
            return Map.of("fulfillmentText", "Đã xảy ra lỗi khi xử lý webhook.");
        }
    }
}
