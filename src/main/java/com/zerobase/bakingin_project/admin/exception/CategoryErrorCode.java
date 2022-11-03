package com.zerobase.bakingin_project.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {

    CATEGORY_NAME_ALREADY_EXIST("이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_EXIST("존재하지 않는 카테고리입니다.");

    private final String value;
}
