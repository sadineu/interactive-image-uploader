package com.example.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Image {

    public Image() { }

    public Image(String imageName) {
        this.imageName = imageName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    @Column(length = 100000)
    private byte[] thumbByte;
    @Transient
    private byte[] imageByte;

}
