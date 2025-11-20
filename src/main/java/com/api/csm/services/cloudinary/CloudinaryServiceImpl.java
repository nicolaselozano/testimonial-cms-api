package com.api.csm.services.cloudinary;

import com.api.csm.interfaces.cloudinary.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.EagerTransformation;
import com.cloudinary.utils.ObjectUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final Logger log = LoggerFactory.getLogger(CloudinaryServiceImpl.class);

    @Resource
    private Cloudinary cloudinary;

    @Override
    public Map<String, String> uploadImageFile(MultipartFile file, String folderName) {
        try {
            String publicId = folderName + "/" + UUID.randomUUID();

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folderName,
                            "public_id", publicId
                    )
            );

            return Map.of(
                    "public_id", (String) uploadResult.get("public_id"),
                    "url", (String) uploadResult.get("secure_url")
            );

        } catch (IOException e) {
            log.error("Error uploading image: {}", e.getMessage());
            return null;
        }
    }

    public Map<String, String> uploadVideoFile(MultipartFile file, String folderName) {
        try {
            String publicId = folderName + "/" + UUID.randomUUID();

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "video",
                            "folder", folderName,
                            "public_id", publicId,
                            "eager", Arrays.asList(
                                    new EagerTransformation().width(300).height(300).crop("pad").audioCodec("none"),
                                    new EagerTransformation().width(160).height(100).crop("crop").gravity("south").audioCodec("none")
                            ),
                            "eager_async", true
                    )
            );

            return Map.of(
                    "public_id", (String) uploadResult.get("public_id"),
                    "url", (String) uploadResult.get("secure_url")
            );

        } catch (IOException e) {
            log.error("Error uploading video: {}", e.getMessage());
            return null;
        }
    }

    public boolean delete(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return true;
        } catch (Exception e) {
            log.error("Error deleting file: {}", e.getMessage());
            return false;
        }
    }

}
