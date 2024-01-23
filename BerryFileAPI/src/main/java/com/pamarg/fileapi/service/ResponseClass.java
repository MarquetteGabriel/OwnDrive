package com.pamarg.fileapi.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClass {
    private String name;
    private String downloadUrl;
    private String type;
    private long size;
    private Date modifiedAt;
}
