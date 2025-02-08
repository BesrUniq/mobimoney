package com.example.mobimoney.controller;

import com.example.mobimoney.model.Code;
import com.example.mobimoney.service.CodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RefillController {

    private final CodeService codeService;

    public RefillController(CodeService codeService) {
        this.codeService = codeService;
    }


        @PostMapping("/generate")
        public ResponseEntity<Code> generateCode(@RequestParam("amount") int balance) {
            Code newCode = codeService.generateCode(balance);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCode);
        }


    @PostMapping("/refill")
    public ResponseEntity<String> refillUserBalance(@RequestParam("code") String code, @RequestParam("user") Long userId) {
        String response = codeService.refillUserBalance(code, userId);
        return ResponseEntity.ok(response);
    }

}

