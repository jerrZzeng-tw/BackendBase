package com.iisi.backendbase;

import com.iisi.backendbase.entity.Item;
import com.iisi.backendbase.entity.ItemUrl;
import com.iisi.backendbase.entity.Role;
import com.iisi.backendbase.entity.User;
import com.iisi.backendbase.entity.UserRole;
import com.iisi.backendbase.repo.ItemRepository;
import com.iisi.backendbase.repo.ItemUrlRepository;
import com.iisi.backendbase.repo.RoleRepository;
import com.iisi.backendbase.repo.UserRepository;
import com.iisi.backendbase.repo.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendBaseApplication.class, args);
    }

    @PostConstruct
    public void initRepo() {
        User user1 = User.builder().username("user1").password(passwordEncoder.encode("123456")).email("user1@mail.com").build();
        User user2 = User.builder().username("user2").password(passwordEncoder.encode("234567")).email("user2@mail.com").build();
        userRepository.save(user1);
        userRepository.save(user2);
        Role admin = Role.builder().roleName("admin").build();
        Role staff = Role.builder().roleName("staff").build();
        roleRepository.save(admin);
        roleRepository.save(staff);
        userRoleRepository.save(UserRole.builder().userId(user1.userId()).roleId(admin.roleId()).build());
        userRoleRepository.save(UserRole.builder().userId(user2.userId()).roleId(staff.roleId()).build());
        Item root = Item.builder().itemName("base").level(0).sort(0).parentId(0L).function(false).build();
        itemRepository.save(root);
        Item sysItem = Item.builder().itemName("系統管理").level(root.level() + 1).sort(0).parentId(root.itemId()).function(false).build();
        itemRepository.save(sysItem);
        Item userItem = Item.builder().itemName("使用者管理").level(sysItem.level() + 1).sort(0).parentId(sysItem.itemId()).function(true).build();
        itemRepository.save(userItem);
        itemUrlRepository.save(ItemUrl.builder().itemId(userItem.itemId()).url("/User_query").comment("查詢所有使用者資料").build());
        itemUrlRepository.save(ItemUrl.builder().itemId(userItem.itemId()).url("/User_add").comment("新增使用者資料").build());
        itemUrlRepository.save(ItemUrl.builder().itemId(userItem.itemId()).url("/User_update").comment("更新使用者資料").build());
        itemUrlRepository.save(ItemUrl.builder().itemId(userItem.itemId()).url("/User_delete").comment("刪除使用者資料").build());

    }
}