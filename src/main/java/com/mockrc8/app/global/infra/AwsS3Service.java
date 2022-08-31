package com.mockrc8.app.global.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.mockrc8.app.global.error.ErrorCode;
import com.mockrc8.app.global.error.exception.upload.EmptyFileException;
import com.mockrc8.app.global.error.exception.upload.FileDownloadFailedException;
import com.mockrc8.app.global.error.exception.upload.FileNotFoundException;
import com.mockrc8.app.global.error.exception.upload.FileUploadFailedException;
import com.mockrc8.app.global.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileV1(String category, MultipartFile multipartFile) {
        validateFileExists(multipartFile);

        String fileName = CommonUtils.buildFileName(category, multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailedException(ErrorCode.FILE_UPLOAD_FAIL);
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString();
    }

    private void validateFileExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new EmptyFileException(ErrorCode.FILE_NOT_EXIST);
        }
    }

    public byte[] downloadFileV1(String resourcePath) {
        validateFileExistsAtUrl(resourcePath);

        S3Object s3Object = amazonS3Client.getObject(bucketName, resourcePath);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new FileDownloadFailedException(ErrorCode.FILE_DOWNLOAD_FAIL);
        }
    }

    private void validateFileExistsAtUrl(String resourcePath) {
        if (!amazonS3Client.doesObjectExist(bucketName, resourcePath)) {
            throw new FileNotFoundException(ErrorCode.FILE_NOT_EXIST);
        }
    }

}