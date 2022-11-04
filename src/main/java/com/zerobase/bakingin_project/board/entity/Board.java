package com.zerobase.bakingin_project.board.entity;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.comment.entity.Comment;
import com.zerobase.bakingin_project.entity.BaseTimeEntity;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String ingredient;

    private String summary;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @ColumnDefault("0")
    private long views; // 조회수

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = RecipeCategory.class)
    @JoinColumn(name = "category_id")
    private RecipeCategory category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE, targetEntity = Member.class)
    @JoinColumn(name = "user_id", updatable = false)
    private Member writer;

    @OneToMany(
            mappedBy = "board",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

}
