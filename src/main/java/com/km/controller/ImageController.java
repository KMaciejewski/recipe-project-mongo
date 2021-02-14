package com.km.controller;

import com.km.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping("recipe/{id}/image-upload")
    public String getImageForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", imageService.findById(id));
        return "recipes/image-upload-form";
    }

    @PostMapping("recipe/{id}/image")
    public String saveImage(@PathVariable String id, @RequestParam MultipartFile image) {
        imageService.save(id, image);
        return String.format("redirect:/recipes/%s", id);
    }

    @SneakyThrows
    @GetMapping("recipe/{id}/image")
    public void getImageFromDB(@PathVariable String id, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        IOUtils.copy(imageService.getImageInputStream(id).block(), response.getOutputStream());
    }
}
