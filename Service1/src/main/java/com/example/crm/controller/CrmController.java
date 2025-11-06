package com.example.crm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/crm")
public class CrmController {

    @PostMapping("/createInvoice")
    public ResponseEntity<?> createInvoice(@RequestBody Map<String, Object> request) {
        if (!request.containsKey("customerId") || !request.containsKey("amount")) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "error", "ValidationError",
                    "message", "Fields 'customerId' and 'amount' are required"
            ));
        }

        RestTemplate restTemplate = new RestTemplate();

        try {
            // CRM → Gateway внутри Docker сети
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://gateway:8081/api/gateway/submitInvoice", request, Map.class
            );

            String invoiceId = "INV-" + (int) (Math.random() * 10000);

            // CRM → Notification (асинхронно)
            Map<String, Object> notification = Map.of(
                    "customerId", request.get("customerId"),
                    "invoiceId", invoiceId,
                    "status", "Created"
            );

            try {
                restTemplate.postForEntity(
                        "http://notification:8083/api/notify", notification, Void.class
                );
            } catch (Exception ex) {
                System.out.println("Notification service unavailable: " + ex.getMessage());
            }

            return ResponseEntity.ok(Map.of(
                    "status", "ok",
                    "crmInvoiceId", invoiceId,
                    "gatewayResponse", response.getBody()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Gateway unreachable",
                    "details", e.getMessage()
            ));
        }
    }
}
