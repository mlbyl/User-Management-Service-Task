package com.mlbyl.usermanagementservicetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilterRequest {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirthFrom;
    private LocalDate dateOfBirthTo;
}
