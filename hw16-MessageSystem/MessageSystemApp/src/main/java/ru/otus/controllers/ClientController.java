package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.crm.dto.ClientData;
import ru.otus.crm.dto.ClientListData;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

@Controller
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final MsClient frontendMsClient;
    private final MsClient databaseMsClient;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ClientController(@Qualifier("frontendService") MsClient frontendMsClient,
                            @Qualifier("databaseService") MsClient databaseMsClient,
                            SimpMessagingTemplate simpMessagingTemplate) {
        this.frontendMsClient = frontendMsClient;
        this.databaseMsClient = databaseMsClient;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/clients")
    public void clients() {
        logger.info("get client list");
        Message outMsg = frontendMsClient.produceMessage(
                databaseMsClient.getName(),
                new ClientListData(),
                MessageType.GET_CLIENTS,
                responseMsg -> simpMessagingTemplate.convertAndSend("/topic/response", responseMsg));
        frontendMsClient.sendMessage(outMsg);
    }

    @MessageMapping("/createClient")
    public void createClient(ClientData clientData) {
        logger.info("create client by DTO {}", clientData);
        Message outMsg = frontendMsClient.produceMessage(
                databaseMsClient.getName(),
                clientData,
                MessageType.SAVE_CLIENT,
                responseMsg -> clients());
        frontendMsClient.sendMessage(outMsg);
    }

}
