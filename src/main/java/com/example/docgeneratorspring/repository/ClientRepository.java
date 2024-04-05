package com.example.docgeneratorspring.repository;

import com.example.docgeneratorspring.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
