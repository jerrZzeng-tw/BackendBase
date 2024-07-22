package com.iisi.backendbase;

import com.iisi.backendbase.entity.Item;
import com.iisi.backendbase.entity.ItemUrl;
import com.iisi.backendbase.entity.Log;
import com.iisi.backendbase.entity.Role;
import com.iisi.backendbase.entity.RoleItem;
import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.entity.UserRole;
import com.iisi.backendbase.repo.ItemRepository;
import com.iisi.backendbase.repo.ItemUrlRepository;
import com.iisi.backendbase.repo.LogRepository;
import com.iisi.backendbase.repo.RoleItemRepository;
import com.iisi.backendbase.repo.RoleRepository;
import com.iisi.backendbase.repo.UserRepository;
import com.iisi.backendbase.repo.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
public class BackendBaseApplication {
    @Resource
    private UserRepository userRepository;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private ItemRepository itemRepository;
    @Resource
    private ItemUrlRepository itemUrlRepository;
    @Resource
    private RoleItemRepository roleItemRepository;
    @Resource
    private LogRepository logRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendBaseApplication.class, args);
    }

    @PostConstruct
    public void initRepo() {
        User admin_user = User.builder().username("admin").password(passwordEncoder.encode("123456")).email("admin@mail.com").build();
        User user_user = User.builder().username("user").password(passwordEncoder.encode("234567")).email("user@mail.com").build();
        userRepository.save(admin_user);
        userRepository.save(user_user);
        Role admin_role = Role.builder().roleName("admin").build();
        Role staff = Role.builder().roleName("staff").build();
        roleRepository.save(admin_role);
        roleRepository.save(staff);
        userRoleRepository.save(UserRole.builder().userId(admin_user.getUserId()).roleId(admin_role.getRoleId()).build());
        userRoleRepository.save(UserRole.builder().userId(admin_user.getUserId()).roleId(staff.getRoleId()).build());
        userRoleRepository.save(UserRole.builder().userId(user_user.getUserId()).roleId(staff.getRoleId()).build());
        Item root = Item.builder().itemName("base").level(0).sort(0).parentId(0L).function(false).build();
        itemRepository.save(root);
        Item sysItem = Item.builder().itemName("系統管理").level(root.getLevel() + 1).sort(0).parentId(root.getItemId()).function(false).build();
        itemRepository.save(sysItem);
        itemUrlRepository.save(ItemUrl.builder().itemId(sysItem.getItemId()).url("/admin/users").comment("查詢所有使用者資料").build());
        itemUrlRepository.save(ItemUrl.builder().itemId(sysItem.getItemId()).url("/admin/user").comment("查詢使用者資料").build());
        itemUrlRepository.save(ItemUrl.builder().itemId(sysItem.getItemId()).url("/admin/roles").comment("查詢所有腳色資料").build());
        Item userItem =
                Item.builder().itemName("使用者管理").level(sysItem.getLevel() + 1).sort(0).parentId(sysItem.getItemId()).function(true).build();
        itemRepository.save(userItem);
        itemUrlRepository.save(ItemUrl.builder().itemId(userItem.getItemId()).url("/user/myInfo").comment("查詢登入者資料").build());
        roleItemRepository.save(RoleItem.builder().roleId(admin_role.getRoleId()).itemId(sysItem.getItemId()).build());
        roleItemRepository.save(RoleItem.builder().roleId(admin_role.getRoleId()).itemId(userItem.getItemId()).build());
        roleItemRepository.save(RoleItem.builder().roleId(staff.getRoleId()).itemId(userItem.getItemId()).build());

        logRepository.save(Log.builder().url("/init").userId("sys").data("init").build());
    }
}