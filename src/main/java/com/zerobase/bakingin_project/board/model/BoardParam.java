package com.zerobase.bakingin_project.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@AllArgsConstructor
public class BoardParam {
    private Long categoryId;
    private String searchType;
    private String searchValue;

    public String getBoardsLink() {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/board/list");
        if(categoryId != null) {
            builder.queryParam("categoryId", categoryId);
        }
        if(searchType != null) {
            builder.queryParam("searchType", searchType)
                    .queryParam("searchValue", searchValue);
        }
        return builder.toUriString();
    }
}
