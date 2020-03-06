package com.example.komunalka.repositories;

import com.example.komunalka.entities.Receipt;
import org.springframework.data.repository.Repository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ReceiptRepository extends Repository<Receipt, Long> {
    List<Receipt> findAll();
    Receipt findById(Long id);
    void save(Receipt receipt);
}
