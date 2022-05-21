package com.example.advancedproject.Repos;

import com.example.advancedproject.Entities.DailyNotesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNotesRepository extends JpaRepository<DailyNotesEntity, Long> {
}
