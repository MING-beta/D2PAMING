package com.example.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "item_image_mapping")
public class ItemImageMapping {

    @Id
    @Column(name = "item_name", length = 100)
    private String itemName;

    @Column(name = "image_filename", length = 200, nullable = false)
    private String imageFilename;

    public ItemImageMapping() {}

    public ItemImageMapping(String itemName, String imageFilename) {
        this.itemName = itemName;
        this.imageFilename = imageFilename;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }
}
