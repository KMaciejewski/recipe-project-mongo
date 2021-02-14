package com.km.service;

import com.km.dto.RecipeDto;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.InputStream;

public interface ImageService {
    void save(String id, MultipartFile image);

    Mono<RecipeDto> findById(String id);

    Mono<InputStream> getImageInputStream(String id);
}
