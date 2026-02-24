package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.time.LocalDateTime;
import java.util.List;
@Controller
public class QRController {

    /*@GetMapping("/")
    public String home() {
        return "index";
    }*/
    @GetMapping("/")
    public String home(Model model) {
        List<QRCodeEntity> history = repository.findAll();
        model.addAttribute("history", history);
        return "index";
    }

    @PostMapping("/generate")
    public String generateQR(@RequestParam String text, Model model) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 400, 400);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

            byte[] qrBytes = pngOutputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(qrBytes);
            QRCodeEntity entity = new QRCodeEntity(text, base64Image, LocalDateTime.now());
            repository.save(entity);

            List<QRCodeEntity> history = repository.findAll();
            model.addAttribute("history", history);

            model.addAttribute("qrImage", base64Image);
            model.addAttribute("downloadImage", base64Image);
            model.addAttribute("text", text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }
    private final QRCodeRepository repository;

    public QRController(QRCodeRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/view/{id}")
    public String viewQR(@PathVariable Long id, Model model) {
        QRCodeEntity entity = repository.findById(id).orElse(null);

        if (entity != null) {
            model.addAttribute("qrImage", entity.getQrImage());
        }

        List<QRCodeEntity> history = repository.findAll();
        model.addAttribute("history", history);

        return "index";
    }
}