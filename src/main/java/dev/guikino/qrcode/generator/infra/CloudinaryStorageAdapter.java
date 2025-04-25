package dev.guikino.qrcode.generator.infra;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import dev.guikino.qrcode.generator.ports.StoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
            InputStream inputStream = new ByteArrayInputStream(fileData);

            Map uploadResult = cloudinary.uploader().upload(inputStream, ObjectUtils.asMap(
                    "public_id", fileName,
                    "folder", "qr-codes",
                    "overwrite", true,
                    "resource_type", "image"
            ));

            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar imagem para o Cloudinary", e);
        }
    }
}
