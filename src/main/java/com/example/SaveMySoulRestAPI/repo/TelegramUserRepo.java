package com.example.SaveMySoulRestAPI.repo;

import com.example.SaveMySoulRestAPI.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface TelegramUserRepo extends JpaRepository<TelegramUser, Long> {
    public List<TelegramUser> findTelegramUsersByIdentifier(String identifier);
}