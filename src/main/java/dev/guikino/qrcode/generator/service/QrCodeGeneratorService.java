package dev.guikino.qrcode.generator.service;

import dev.guikino.qrcode.generator.dto.QrCodeGenerateResponse;
import dev.guikino.qrcode.generator.ports.StoragePort;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import javax.imageio.ImageIO;

@Service
public class QrCodeGeneratorService {

    private final StoragePort storagePort;

    public QrCodeGeneratorService(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text) {
        try {
            // Geração do QR Code
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 300, 300, hints);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

            // Conversão da imagem para byte[]
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] qrCodeBytes = baos.toByteArray();

            // Upload no storage (Cloudinary)
            String url = storagePort.uploadFile(qrCodeBytes, "qrcode_" + System.currentTimeMillis(), "image/png");

            return new QrCodeGenerateResponse(url);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar ou enviar QR Code", e);
        }
    }
}
