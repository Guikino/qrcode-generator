package dev.guikino.qrcode.generator.ports;

public interface StoragePort {
  public String uploadFile(byte[] fileData, String fileName, String contentType);
}
