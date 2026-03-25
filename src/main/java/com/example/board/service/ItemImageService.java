package com.example.board.service;

import com.example.board.domain.ItemImageMapping;
import com.example.board.repository.ItemImageMappingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service("itemImageService")
@Transactional(readOnly = true)
public class ItemImageService {

    private final ItemImageMappingRepository itemImageMappingRepository;
    private final ObjectMapper objectMapper;
    private Map<String, String> jsonCache = new HashMap<>();

    public ItemImageService(ItemImageMappingRepository itemImageMappingRepository, ObjectMapper objectMapper) {
        this.itemImageMappingRepository = itemImageMappingRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            ClassPathResource resource = new ClassPathResource("items_mapping.json");
            if (resource.exists()) {
                try (InputStream is = resource.getInputStream()) {
                    jsonCache = objectMapper.readValue(is, new TypeReference<Map<String, String>>() {});
                    System.out.println("Loaded " + jsonCache.size() + " item mappings from JSON.");
                }
            } else {
                System.out.println("items_mapping.json not found in classpath. Using empty JSON cache.");
            }
        } catch (Exception e) {
            System.err.println("Failed to load JSON item mapping fallback: " + e.getMessage());
            jsonCache = new HashMap<>();
        }
    }

    public String getImagePath(String itemName) {
        if (itemName == null || itemName.trim().isEmpty()) {
            return "/images/items/all/ring1.gif"; // default image
        }

        try {
            // 1. Try DB first
            Optional<ItemImageMapping> dbMapping = itemImageMappingRepository.findById(itemName);
            if (dbMapping.isPresent() && dbMapping.get().getImageFilename() != null) {
                return "/images/items/all/" + dbMapping.get().getImageFilename();
            }
        } catch (Exception e) {
            // If DB connection fails or table isn't ready
            System.err.println("DB query for item mapping failed: " + e.getMessage());
        }

        // 2. Fallback to JSON cache
        String jsonMapping = jsonCache.get(itemName);
        if (jsonMapping != null) {
            return "/images/items/all/" + jsonMapping;
        }

        // 3. Ultimate default
        return "/images/items/all/ring1.gif";
    }

    public java.util.List<String> getAllMappedItemNames() {
        java.util.List<String> items = new java.util.ArrayList<>(jsonCache.keySet());
        java.util.Collections.sort(items);
        return items;
    }
}
