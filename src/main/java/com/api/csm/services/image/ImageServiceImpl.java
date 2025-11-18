package com.api.csm.services.image;

import com.api.csm.interfaces.cloudinary.CloudinaryService;
import com.api.csm.interfaces.image.ImageService;
import com.api.csm.models.Image;
import com.api.csm.repository.ImageRepository;
import com.api.csm.utils.image.ImageModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private CloudinaryService cloudinaryService;
    private ImageRepository imageRepository;


    public ResponseEntity<Map> uploadImage(ImageModel imageModel) {
        try {
            if (imageModel.getName().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            if (imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
            image.setName(imageModel.getName());
            image.setUrl(cloudinaryService.uploadFile(imageModel.getFile(), "folder_1"));
            if(image.getUrl() == null) {
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
