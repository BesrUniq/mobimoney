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

        Optional<Code> optionalCode = codeRepository.findByCode(code);
        if (optionalCode.isEmpty()) {
            return "No code!";
        }

        Code foundCode = optionalCode.get();
        int codeBalance = foundCode.getBalance();

        if (foundCode.isUsed()) {
            return "This code has already been used!";
        }


        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return "User not found!";
        }

        User user = optionalUser.get();
        user.setBalance(user.getBalance() + codeBalance);
        userRepository.save(user);

        foundCode.setUsed(true);
        foundCode.setUsedBy(user);
        foundCode.setUsedAt(LocalDateTime.now());
        codeRepository.save(foundCode);

        return "User balance updated successfully! New balance: " + user.getBalance();
    }
}
