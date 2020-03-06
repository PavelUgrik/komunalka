package com.example.komunalka.repositories;

import com.example.komunalka.entities.Tarif;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface TarifRepository extends Repository<Tarif, Long> {
    List<Tarif> findAll();
    Tarif findById(Long id);
    void save(Tarif tarif);
    Tarif findFirstByOrderByIdDesc();
}
