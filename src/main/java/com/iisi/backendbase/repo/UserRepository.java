package com.iisi.backendbase.repo;

import com.iisi.backendbase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value = "select u.username,email,r.role_name as roleName from user_info u,user_role ur,role r where u.user_id = ur.id and ur.role_id = r.role_id", nativeQuery = true)
    List<UserRoleDTO> findAllUserRoleNative();

    Optional<User> findByUsername(String username);

    //自定義DTO
    interface UserRoleDTO {
        String getUsername();

        String getEmail();

        String getRoleName();
    }
}