package com.zerobase.bakingin_project.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CKResponse {
    // 업로드한 이미지 개수
    private Integer uploaded;
    // 파일명
    private String fileName;
    // 이미지를 볼 수 있는 주소
    private String url;
}
