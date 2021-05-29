package com.example.app.services;

import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener implements MessageListener {

     ImageService imageService;
    @Autowired
    public RabbitMQListener(ImageService imageService){
        this.imageService = imageService;
    }
    @SneakyThrows
    public void onMessage(Message message) {
        String imageId = new String(message.getBody());
        System.out.println("Consuming Message from rabbit image id - " + new String(message.getBody()));
        try {
            imageService.updateImageThumb(imageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
