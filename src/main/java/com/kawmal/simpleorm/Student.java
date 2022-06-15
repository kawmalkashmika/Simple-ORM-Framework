package com.kawmal.simpleorm;

import com.kawmal.simpleorm.annotation.Entity;
import com.kawmal.simpleorm.annotation.Id;

@Entity
public class Student {
    @Id
    private String studentId;
    private String studentName;
    private String age;

}
