package com.example.app.services;

import com.example.app.domain.Image;
import org.springframework.http.ResponseEntity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
   BodyBuilder uploadImage(MultipartFile[] files) throws IOException;
   List<Image> getImages() throws IOException;
   void updateImageThumb(String imageId)throws IOException, Exception;
}
