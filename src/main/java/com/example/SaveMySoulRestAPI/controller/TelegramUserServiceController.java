package com.example.SaveMySoulRestAPI.controller;

import com.example.SaveMySoulRestAPI.model.TelegramUser;
import com.example.SaveMySoulRestAPI.model.TelegramUserService;
import com.example.SaveMySoulRestAPI.repo.TelegramUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/TelegramUserServiceController")
public class TelegramUserServiceController {

    @Autowired
    private TelegramUserRepo telegramUserRepo;

    @Value("${telegram.bot.api.url}")
    private String bot_api_url;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final Logger logger = LoggerFactory.getLogger(TelegramUserServiceController.class);

    @PostMapping("sendSOS")
    public String sendSOS(@RequestBody TelegramUserService telegramUserService) {
        List<TelegramUser> telegramUsers =  telegramUserRepo.findTelegramUsersByIdentifier(telegramUserService.getIdentifier());
        String result = "";

        for (TelegramUser telegramUser : telegramUsers) {
            telegramUser.setTelegramMessage(telegramUser.getTelegramMessage() + " " + telegramUserService.getLocation());
            String url =  String.format("%s/sendMessage?chat_id=%d&text=%s&parse_mode=html", bot_api_url, Long.valueOf(telegramUser.getTelegramId()), telegramUser.getTelegramMessage());

            try {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
                logger.info(response.toString());
                result = "SOS надіслано!";
            } catch (Exception e) {
                logger.error(e.getMessage());
                result = "Помилка в надсилані SOS: " + e.getMessage();
            }
        }

        return result;
    }
}