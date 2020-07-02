package com.km.service;

import com.km.dto.RecipeDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface ImageService {
    void save(String id, MultipartFile image);

    RecipeDto findById(String id);

    InputStream getImageInputStream(String id);
}
