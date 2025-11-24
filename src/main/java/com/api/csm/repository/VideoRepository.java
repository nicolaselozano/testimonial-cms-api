package com.api.csm.repository;

import com.api.csm.models.Image;
import com.api.csm.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoRepository extends JpaRepository<Video, UUID> {
}
