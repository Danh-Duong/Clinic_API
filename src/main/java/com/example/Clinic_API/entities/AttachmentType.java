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
public class AttachmentType extends BaseEntity{
    private String name;
    private String code;
    private Boolean isActive;

    @OneToMany(mappedBy = "attachmentType")
    private List<Attachment> attachments;

    public static enum AttachmentTypeEnum{
        AVATAR,POST
    }
}
