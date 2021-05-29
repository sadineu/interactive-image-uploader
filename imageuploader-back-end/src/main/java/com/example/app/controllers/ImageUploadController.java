package com.example.app.controllers;

import com.example.app.domain.Image;
import java.io.IOException;
import java.util.List;
import com.example.app.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/v1/images")
public class ImageUploadController {

    @Autowired
    ImageService imageService;

    @PostMapping("/upload")
    public BodyBuilder uplaodImage(@RequestParam("imageFiles") MultipartFile[] files) throws IOException {
        return imageService.uploadImage(files);
    }

    @GetMapping(path = { "/get" })
    public List<Image> getImage() throws IOException {
        return imageService.getImages();
    }


}
