package com.km.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
    public void save(Long id, MultipartFile image) {
        final Byte[] bytes = new Byte[image.getBytes().length];
        int i = 0;
        for (byte b : image.getBytes()) {
            bytes[i++] = b;
        }

        findEntityById(id).setImage(bytes);
    }

    @Override
    public RecipeDto findById(Long id) {
        return toDtoConverter.convert(findEntityById(id));
    }

    @Override
    public InputStream getImageInputStream(Long id) {
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

    private Recipe findEntityById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }
}
