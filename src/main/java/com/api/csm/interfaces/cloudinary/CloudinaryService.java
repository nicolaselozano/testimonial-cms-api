package com.api.csm.interfaces.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudinaryService {
    Map<String, String> uploadImageFile(MultipartFile file, String folderName);
    Map<String, String> uploadVideoFile (MultipartFile file, String folderName);
    boolean delete(String publicId);
}
