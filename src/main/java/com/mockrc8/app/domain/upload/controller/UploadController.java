package com.mockrc8.app.domain.upload.controller;

import com.mockrc8.app.global.infra.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("category") String category,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(category, multipartFile);
    }
}
