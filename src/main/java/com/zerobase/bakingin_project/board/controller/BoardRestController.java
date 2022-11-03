package com.zerobase.bakingin_project.board.controller;

import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
public class BoardRestController {
    private final BoardService boardService;

    @Value("${ck.image.folder}")
    private String CKImageFolder;

    @PostMapping("/board/image")
    public ResponseEntity<CKResponse> ckImageUpload(MultipartFile upload) {
        CKResponse ckResponse = boardService.ckImageUpload(upload);
        return ResponseEntity.ok(ckResponse);
    }

    // 사진 경로를 요청하면 사진을 보내 주는 컨트롤러
    @GetMapping("/images/{imageName}")
    public ResponseEntity<byte[]> showImage(@PathVariable String imageName) {
        // 파일이 있는지 없는지 확인
        File file = new File(CKImageFolder, imageName);
        if (!file.exists())
            return null;

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        String extension = imageName.substring(imageName.lastIndexOf(".")).toUpperCase();
        MediaType type = null;
        if (extension.equals("JPG"))
            type = MediaType.IMAGE_JPEG;
        else if (extension.equals("PNG"))
            type = MediaType.IMAGE_PNG;
        else if (extension.equals("GIF"))
            type = MediaType.IMAGE_GIF;
        headers.setContentType(type);
        headers.add("Content-Disposition", "inline;filename="  + imageName);
        try {
            return ResponseEntity.ok().headers(headers).body(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
