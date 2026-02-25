package dev.guikino.qrcode.generator.controller;

import dev.guikino.qrcode.generator.dto.QrCodeGenerateResponse;
import dev.guikino.qrcode.generator.dto.QrCodeGeneratedRequest;
import dev.guikino.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrcode")
@CrossOrigin(origins = {"https://zappi-qr.vercel.app/"}) 
public class QrCodeController {
    private final QrCodeGeneratorService qrCodeService;

    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API is up and running!");
    }

    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGeneratedRequest request) {
        try {
            QrCodeGenerateResponse response = this.qrCodeService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
