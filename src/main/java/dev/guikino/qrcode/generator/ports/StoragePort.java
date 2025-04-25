package dev.guikino.qrcode.generator.ports;

import java.io.ByteArrayInputStream;

public interface StoragePort {
  public String uploadFile(byte[] fileData, String fileName, String contentType);

  String uploadFile(ByteArrayInputStream inputStream);
}
