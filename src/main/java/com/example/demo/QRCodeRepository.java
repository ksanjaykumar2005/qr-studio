package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, Long> {
}