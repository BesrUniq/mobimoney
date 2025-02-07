package com.example.mobimoney.repository;

import com.example.mobimoney.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> findByCode(String code);
}
