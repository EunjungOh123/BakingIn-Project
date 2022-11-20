package com.zerobase.bakingin_project.board.like.dto;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeartDto {
    private Board board;
    private Member user;
}
