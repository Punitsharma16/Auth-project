package com.example.auth_app_backend.S3Config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
@Service
public class QRCODES3Service {
    @Autowired
    S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.endpoint}")
    private String endpoint;


    public  String generateAndUploadQR(String displayId,String menuUrl) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter= new QRCodeWriter();
        BitMatrix bitMatrix=qrCodeWriter.encode(menuUrl, BarcodeFormat.QR_CODE,
                400, 400);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        byte[] qrBytes = baos.toByteArray();
        String s3Key = "qr-codes/" + displayId + ".png";
       s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(s3Key)
                        .contentType("image/png")
                        .build(),
                RequestBody.fromBytes(qrBytes)
        );
        String publicUrl = endpoint + "/" + bucketName + "/" + s3Key;
      return  publicUrl;
    }


}
