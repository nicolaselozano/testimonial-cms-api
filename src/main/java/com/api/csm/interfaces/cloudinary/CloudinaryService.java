package com.api.csm.interfaces.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadFile (MultipartFile file, String folderName);
}
