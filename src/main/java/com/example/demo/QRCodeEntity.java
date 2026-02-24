package com.example.demo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QRCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String text;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String qrImage;

    private LocalDateTime createdAt;

    public QRCodeEntity() {}

    public QRCodeEntity(String text, String qrImage, LocalDateTime createdAt) {
        this.text = text;
        this.qrImage = qrImage;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public String getQrImage() { return qrImage; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}