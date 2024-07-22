package com.iisi.backendbase.framework.services;

import com.iisi.backendbase.entity.Log;
import com.iisi.backendbase.repo.LogRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Resource
    private LogRepository logRepository;

    public void saveLog(String url, String user, String data) {
        //        saveLog(Log.builder().url(url).user(user).data(data).build());
    }

    public void saveLog(Log log) {
        logRepository.save(log);
    }
}