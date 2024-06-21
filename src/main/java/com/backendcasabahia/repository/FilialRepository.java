package com.backendcasabahia.repository;

import com.backendcasabahia.model.FilialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository  extends JpaRepository<FilialEntity, Long> {
}
