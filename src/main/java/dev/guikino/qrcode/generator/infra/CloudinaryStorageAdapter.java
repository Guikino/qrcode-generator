package dev.guikino.qrcode.generator.infra;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.guikino.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class CloudinaryStorageAdapter implements StoragePort {

    private final Cloudinary cloudinary;

    public CloudinaryStorageAdapter(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    @Override
    public String uploadFile(byte[] fileData, String fileName, String contentType) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
            return uploadFile(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter byte[] para upload no Cloudinary", e);
        }
    }

    @Override
    public String uploadFile(ByteArrayInputStream inputStream) {
        try {
            byte[] bytes = inputStream.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String dataUrl = "data:image/png;base64," + base64;

            Map uploadResult = cloudinary.uploader().upload(dataUrl, ObjectUtils.emptyMap());
            System.out.println("Resultado do Cloudinary: " + uploadResult);

            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao enviar imagem para o Cloudinary", e);
        }
    }
}
