package com.api.csm.controllers.cloudinary;

import com.api.csm.interfaces.image.ImageService;
import com.api.csm.repository.ImageRepository;
import com.api.csm.utils.image.FileModel;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class ImageController {

    private ImageRepository imageRepository;
    private ImageService imageService;

    @PostMapping(path = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map> upload( @ModelAttribute FileModel fileModel) {
        try {
            return imageService.uploadImage(fileModel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}