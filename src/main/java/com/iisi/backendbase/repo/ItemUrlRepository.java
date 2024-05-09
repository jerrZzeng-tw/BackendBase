package com.iisi.backendbase.repo;

import com.iisi.backendbase.entity.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemUrlRepository extends JpaRepository<ItemUrl, Long>, JpaSpecificationExecutor<ItemUrl> {
}