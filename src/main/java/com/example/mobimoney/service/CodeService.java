package com.example.mobimoney.service;

import com.example.mobimoney.model.Code;
import com.example.mobimoney.model.User;
import com.example.mobimoney.repository.CodeRepository;
import com.example.mobimoney.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    public CodeService(CodeRepository codeRepository, UserRepository userRepository) {
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
    }

    public Code generateCode(int amount) {

        String newCode = UUID.randomUUID().toString();
        Code code = new Code(newCode, amount);
        codeRepository.save(code);
        return code;
    }

    @Transactional
    public String refillUserBalance(String code, Long userId) {
        try {
            Code foundCode = codeRepository.findByCode(code)
                    .orElseThrow(() -> new IllegalArgumentException("No code!"));

            if (foundCode.isUsed()) {
                throw new IllegalStateException("This code has already been used!");
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found!"));

            int codeBalance = foundCode.getBalance();
            user.setBalance(user.getBalance() + codeBalance);
            userRepository.save(user);

            foundCode.setUsed(true);
            foundCode.setUsedBy(user);
            foundCode.setUsedAt(LocalDateTime.now());
            codeRepository.save(foundCode);

            return "User balance updated successfully! New balance: " + user.getBalance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to refill user balance", e);
        } finally {
            System.out.println("Refill user balance operation finished");
        }
    }
}
