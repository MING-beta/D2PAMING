package com.example.board.repository;

import com.example.board.domain.ItemImageMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageMappingRepository extends JpaRepository<ItemImageMapping, String> {
}
