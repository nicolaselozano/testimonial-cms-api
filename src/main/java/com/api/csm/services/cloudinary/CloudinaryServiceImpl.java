package com.api.csm.services.cloudinary;

import com.api.csm.interfaces.cloudinary.CloudinaryService;
import com.cloudinary.Cloudinary;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryServiceImpl implements CloudinaryService {
    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);
    @Resource
    private Cloudinary cloudinary;

    public String uploadFile (MultipartFile file,String folderName){
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        }catch (IOException e){
            log.error(e.getMessage());
            return null;
        }
    }
}
