package com.example.Clinic_API.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class PostType extends BaseEntity{
    private String name;
    private String code;
    private Boolean isActive= Boolean.TRUE;

    @OneToMany(mappedBy = "postType")
    private List<Post> posts;
}
