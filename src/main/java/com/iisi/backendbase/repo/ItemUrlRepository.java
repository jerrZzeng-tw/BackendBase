package com.iisi.backendbase.repo;

import com.iisi.backendbase.entity.ItemUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemUrlRepository extends JpaRepository<ItemUrl, Long>, JpaSpecificationExecutor<ItemUrl> {
    @Query(value = "select role_name from role where role_id in(select  role_id from user_item where item_id in( select item_id from item_url where url = :url));", nativeQuery = true)
    List<ItemUrlRoleDTO> findUrlRoleNative(@Param("url") String url);

    Optional<ItemUrl> findByUrl(String url);

    interface ItemUrlRoleDTO {
        String getRoleName();
    }
}