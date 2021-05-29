package com.example.app.services;

import com.example.app.domain.Image;
import com.example.app.repositories.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl implements ImageService {
    static String imageDir = "resources/images/";
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    RabbitMQSender rabbitMQSender;
    @Autowired
    WebSocketNotificationService webSocketNotificationService;
    @Transactional
    public BodyBuilder uploadImage(MultipartFile[] files) throws IOException {
        for(MultipartFile file:files){
            Image img = new Image(file.getOriginalFilename());
            img = imageRepository.save(img);
            String uploadDir = imageDir + img.getId();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + file.getOriginalFilename(), ioe);
            }
            System.out.println("Image Uploaded");
            rabbitMQSender.send(img.getId());
        }

        return ResponseEntity.status(HttpStatus.OK);
    }

    public List<Image> getImages() throws IOException {
        final List<Image> images = imageRepository.findAll();
        for (Image image : images) {
            String dir = imageDir + image.getId() + "/" + image.getImageName();
            Path path = Paths.get(dir);
            image.setImageByte(Files.readAllBytes(path));
        }
        List<Image>uploadedImages = images.stream()
                .filter(image -> image.getThumbByte() != null)
                .collect(Collectors.toList());
        return uploadedImages;
    }

    @Transactional
    public void updateImageThumb(String imageId) throws Exception {
        Long id = Long.valueOf(imageId);
        Optional<Image> optionalImage = imageRepository.findById(id);
        if(optionalImage.isPresent()){
            Image image = optionalImage.get();
            String path = imageDir + image.getId() + "/" + image.getImageName();
            File file = new File(path);
            BufferedImage bImage = ImageIO.read(file );
            byte[] thumb = resizeImage(bImage, 200,200, getFileExtension(path));
            image.setThumbByte(thumb);
            imageRepository.save(image);
            webSocketNotificationService.sendNotification();
        }
    }

    byte[] resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight, String format) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
                .size(targetWidth, targetHeight)
                .outputFormat(format)
                .outputQuality(1)
                .toOutputStream(outputStream);
        byte[] data = outputStream.toByteArray();
        return data;
    }

    public String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }
}
