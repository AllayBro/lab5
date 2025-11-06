package com.example.gateway_service.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/gateway")
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/submitInvoice")
    public ResponseEntity<?> submitInvoice(@RequestBody Map<String, Object> json) {
        String xmlRequest = String.format("""
                <InvoiceRequest>
                    <CustomerId>%s</CustomerId>
                    <Amount>%s</Amount>
                    <Description>%s</Description>
                </InvoiceRequest>
                """,
                json.get("customerId"),
                json.get("amount"),
                json.getOrDefault("description", "")
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<String> entity = new HttpEntity<>(xmlRequest, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://billing:8082/api/billing/invoice", entity, String.class);

            return ResponseEntity.ok(Map.of(
                    "billingResponse", response.getBody()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Billing service not reachable",
                    "details", e.getMessage()
            ));
        }
    }
}