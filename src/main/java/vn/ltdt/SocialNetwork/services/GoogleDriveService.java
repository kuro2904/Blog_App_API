package vn.ltdt.SocialNetwork.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleDriveService {

    private static final String APPLICATION_NAME = "BlogApp";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Value("${google.service.account.key}")
    private String serviceAccountKeyPath;

    @Value("${googleDrive.folderId}")
    private String folderId;

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleCredentials credential = GoogleCredentials
                .fromStream(new FileInputStream(serviceAccountKeyPath))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        return new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credential))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private String uploadFile(MultipartFile file) {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(UUID.randomUUID().toString());
            fileMetadata.setParents(Collections.singletonList(folderId));

            InputStreamContent mediaContent = new InputStreamContent(
                    file.getContentType(), new ByteArrayInputStream(file.getBytes()));

            com.google.api.services.drive.model.File uploadedFile = getDriveService().files()
                    .create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            // Make file public
            Permission permission = new Permission();
            permission.setType("anyone");
            permission.setRole("reader");
            getDriveService().permissions()
                    .create(uploadedFile.getId(), permission)
                    .execute();

            return "https://drive.google.com/uc?id=" + uploadedFile.getId();
        } catch (Exception e) {
            log.error("Upload failed", e);
            return null;
        }
    }

    public List<String> uploadMultipleFiles(List<MultipartFile> files) {
        List<String> links = new ArrayList<>();
        files.forEach(file -> {
            String link = uploadFile(file);
            if (link != null) {
                links.add(link);
            }
        });
        return links;
    }

}

