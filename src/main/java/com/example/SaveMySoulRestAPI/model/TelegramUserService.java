package com.example.SaveMySoulRestAPI.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class TelegramUserService {
    @NonNull
    private String identifier;

    @NonNull
    private String location;

    public TelegramUserService() {

    }

    public TelegramUserService(@NonNull String location, @NonNull String identifier) {
        this.location = location;
        this.identifier = identifier;
    }
}
