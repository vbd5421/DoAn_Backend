package com.doan.backend.repository;


import com.doan.backend.model.Sliders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SlidersRepository extends JpaRepository<Sliders,Long> {
    List<Sliders> getAllByStatusIsTrue();

    @Query("SELECT s FROM Sliders s WHERE s.active= true AND s.status = true")
    List<Sliders> getAllByActiveAndStatusIsTrue();

    Sliders findAllByName(String name);

    @Query("SELECT s.name FROM Sliders s WHERE s.name=:name")
    String getImageByName(String name);

    @Query("SELECT s.pathFile FROM Sliders s WHERE s.name=:name")
    String getPathFileByName(String name);
}
