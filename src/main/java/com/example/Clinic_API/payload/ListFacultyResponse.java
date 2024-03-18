package com.example.Clinic_API.payload;

import com.example.Clinic_API.entities.Faculty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFacultyResponse extends BaseResponse{
    private List<Faculty> faculties;
}
