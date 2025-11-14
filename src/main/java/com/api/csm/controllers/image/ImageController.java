package com.api.csm.controllers.image;

import com.api.csm.interfaces.image.ImageService;
import com.api.csm.repository.ImageRepository;
import com.api.csm.utils.image.ImageModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ImageController {

    private ImageRepository imageRepository;
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Map> upload(ImageModel imageModel) {
        try {
            return imageService.uploadImage(imageModel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}