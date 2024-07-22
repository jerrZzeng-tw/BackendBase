package com.iisi.backendbase.framework.services;

import com.iisi.backendbase.entity.ItemUrl;
import com.iisi.backendbase.repo.ItemUrlRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    @Resource
    private ItemUrlRepository itemUrlRepository;

    //    @Cacheable(value = "authCache", keyGenerator = "customKeyGenerator")
    @Transactional(readOnly = true)
    public List<String> findRolesByItemUrl(final String url) {
        log.info("findRolesByItemUrl: {}", url);
        Optional<ItemUrl> itemUrl = itemUrlRepository.findByUrl(url);
        return itemUrl.map(value -> value.getItem().getRoles().stream().map(t -> t.getRoleId().toString()).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
}