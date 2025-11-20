package com.api.csm.interfaces.video;

import com.api.csm.utils.image.FileModel;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface VideoService {
    public ResponseEntity<Map> uploadVideo(FileModel fileModel);
}
