package com.api.csm.interfaces.image;

import com.api.csm.utils.image.FileModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {
    ResponseEntity<Map> uploadImage(FileModel fileModel);

}
