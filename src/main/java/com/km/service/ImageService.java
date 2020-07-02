package com.km.service;

import com.km.dto.RecipeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface ImageService {
    void save(Long id, MultipartFile image);

    RecipeDto findById(Long id);

    InputStream getImageInputStream(Long id);
}
