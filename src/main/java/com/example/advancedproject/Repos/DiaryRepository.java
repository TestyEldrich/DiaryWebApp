package com.example.advancedproject.Repos;

import com.example.advancedproject.Entities.DiaryEntity;
import com.example.advancedproject.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
}
