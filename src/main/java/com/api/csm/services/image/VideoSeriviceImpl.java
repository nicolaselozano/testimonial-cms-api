package com.api.csm.services.image;

import com.api.csm.config.cloudinary.CloudinaryConfiguration;
import com.api.csm.config.properties.CloudProperties;
import com.api.csm.interfaces.cloudinary.CloudinaryService;
import com.api.csm.interfaces.video.VideoService;
import com.api.csm.models.Video;
import com.api.csm.repository.VideoRepository;
import com.api.csm.utils.image.FileModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class VideoSeriviceImpl implements VideoService {

    private final CloudinaryService cloudinaryService;
    private final VideoRepository videoRepository;
    private final CloudProperties cloudProperties;
    public ResponseEntity<Map> uploadVideo(FileModel fileModel) {
        try {
            if (fileModel.getName().isEmpty() || fileModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Map<String, String> result = cloudinaryService.uploadVideoFile(
                    fileModel.getFile(), cloudProperties.getFolderName()
            );

            if (result == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Upload failed"));
            }

            String publicId = result.get("public_id");
            String url = result.get("url");

            Video image = new Video();
            image.setName(fileModel.getName());
            image.setUrl(url);
            image.setPublicId(publicId);

            try {
                videoRepository.save(image);
            } catch (Exception e) {
                cloudinaryService.delete(publicId);
                throw e;
            }

            return ResponseEntity.ok(Map.of("url", url));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("error", "Unexpected error"));
        }
    }

}
