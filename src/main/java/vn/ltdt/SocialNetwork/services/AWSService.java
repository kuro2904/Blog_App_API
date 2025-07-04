package vn.ltdt.SocialNetwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AWSService {

    private final S3Client s3Client;

    @Value("${aws.bucket}")
    private String bucketName;

    public String upload(MultipartFile file)  {
        try {
            String key = String.format("%s_%s",UUID.randomUUID(),file.getOriginalFilename());

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

            return String.format("https://%s.s3.amazonaws.com/%s",bucketName,key);
        }catch (IOException e) {
            log.error("Upload failed",e);
            return null;
        }
    }



}
