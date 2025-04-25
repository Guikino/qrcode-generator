package dev.guikino.qrcode.generator.controller;

import dev.guikino.qrcode.generator.dto.QrCodeGenerateResponse;
import dev.guikino.qrcode.generator.dto.QrCodeGeneratedRequest;
import dev.guikino.qrcode.generator.service.QrCodeGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {
  private final QrCodeGeneratorService qrCodeService;

    public QrCodeController(QrCodeGeneratorService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    //post
    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGeneratedRequest request){
      try {
          QrCodeGenerateResponse response = this.qrCodeService.generateAndUploadQrCode(request.text());
          return ResponseEntity.ok(response);
      }   catch(Exception e){
        e.printStackTrace(); // ou logger.error("Erro ao gerar QR", e);
        return ResponseEntity.internalServerError().build();
    }

}

}
