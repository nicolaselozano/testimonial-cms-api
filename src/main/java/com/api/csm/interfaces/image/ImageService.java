package com.api.csm.interfaces.image;

import com.api.csm.utils.image.ImageModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {
    ResponseEntity<Map> uploadImage(ImageModel imageModel);

}
