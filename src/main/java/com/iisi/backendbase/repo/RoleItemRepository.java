package com.iisi.backendbase.repo;

import com.iisi.backendbase.entity.RoleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleItemRepository extends JpaRepository<RoleItem, Long>, JpaSpecificationExecutor<RoleItem> {

}