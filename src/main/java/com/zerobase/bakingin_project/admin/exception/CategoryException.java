package com.zerobase.bakingin_project.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryException extends RuntimeException{
    private CategoryErrorCode categoryErrorCode;
    private String message;

    public CategoryException(CategoryErrorCode categoryErrorCode){
        this.categoryErrorCode = categoryErrorCode;
        this.message = categoryErrorCode.getValue();
    }
}
