package com.api.csm.utils.image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileModel {
    private String name;
    private MultipartFile file;
}
