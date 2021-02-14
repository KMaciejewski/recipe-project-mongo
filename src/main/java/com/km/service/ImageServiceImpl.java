package com.km.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.model.Recipe;
import com.km.repository.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeEntityToDtoConverter toDtoConverter;

    @SneakyThrows
    @Override
    public void save(String id, MultipartFile image) {
        final Byte[] bytes = new Byte[image.getBytes().length];
        int i = 0;
        for (byte b : image.getBytes()) {
            bytes[i++] = b;
        }

        findEntityById(id).block().setImage(bytes);
    }

    @Override
    public Mono<RecipeDto> findById(String id) {
        return findEntityById(id).map(toDtoConverter::convert);
    }

    @Override
    public Mono<InputStream> getImageInputStream(String id) {
        final Recipe recipe = findEntityById(id).block();

        if (recipe.getImage() != null) {
            final byte[] bytes = new byte[recipe.getImage().length];
            int i = 0;
            for (Byte b : recipe.getImage()) {
                bytes[i++] = b;
            }
            return Mono.just(new ByteArrayInputStream(bytes));
        }
        return Mono.just(new ByteArrayInputStream(new byte[0]));
    }

    private Mono<Recipe> findEntityById(String id) {
        return recipeReactiveRepository.findById(id);
    }
}
