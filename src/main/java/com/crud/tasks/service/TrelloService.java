package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TrelloService {
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;
    private final AdminConfig adminConfig;

    public static final String SUBJECT = "Tasks: New Trello card";

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> emailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                        null
                )));
        return newCard;
    }

//    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto) {
//        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
//        Mail mail = Mail.builder()
//                .mailTo(adminConfig.getAdminMail())
//                .subject(SUBJECT)
//                .message("New card: " + trelloCardDto.getName() + " has been created on your Trello account")
//                .build();
//        ofNullable(newCard).ifPresent(card -> emailService.send(mail));
//        return newCard;
//    }


}