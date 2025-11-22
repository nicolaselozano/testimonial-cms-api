package com.api.csm.repository.media;

import com.api.csm.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {

    //Para buscar por url
    Optional<Media> findByUrl(String url);
}
