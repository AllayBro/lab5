package com.example.billing.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    @PostMapping(value = "/invoice", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createInvoice(@RequestBody String xmlRequest) {
        System.out.println("Received XML:\n" + xmlRequest);

        String xmlResponse = """
                <InvoiceResponse>
                    <InvoiceId>INV-2001</InvoiceId>
                    <Status>Created</Status>
                    <Message>Invoice processed successfully</Message>
                </InvoiceResponse>
                """;

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_XML)
                .body(xmlResponse);
    }
}
