package com.km.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;
    private final RecipeEntityToDtoConverter toDtoConverter;

    @SneakyThrows
    @Override
    @Transactional
    public void save(String id, MultipartFile image) {
        final Byte[] bytes = new Byte[image.getBytes().length];
        int i = 0;
        for (byte b : image.getBytes()) {
            bytes[i++] = b;
        }

        findEntityById(id).setImage(bytes);
    }

    @Override
    public RecipeDto findById(String id) {
        return toDtoConverter.convert(findEntityById(id));
    }

    @Override
    public InputStream getImageInputStream(String id) {
        final Recipe recipe = findEntityById(id);

        if (recipe.getImage() != null) {
            final byte[] bytes = new byte[recipe.getImage().length];
            int i = 0;
            for (Byte b : recipe.getImage()) {
                bytes[i++] = b;
            }
            return new ByteArrayInputStream(bytes);
        }
        return new ByteArrayInputStream(new byte[0]);
    }

    private Recipe findEntityById(String id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity not found"));
    }
}
