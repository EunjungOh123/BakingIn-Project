package com.zerobase.bakingin_project.admin.category.entity;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String categoryName;
    private boolean usingCategory;

    @OneToMany(
            mappedBy = "category",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Board> boards = new ArrayList<>();
}
