package com.zerobase.bakingin_project.admin.category.entity;

import com.zerobase.bakingin_project.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class RecipeCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    private boolean usingYn;
}
