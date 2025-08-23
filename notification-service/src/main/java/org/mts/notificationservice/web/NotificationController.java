package org.mts.notificationservice.web;

import org.modelmapper.ModelMapper;
import org.mts.notificationservice.dtos.Message;
import org.mts.notificationservice.entities.Notification;
import org.mts.notificationservice.services.INotificationService;
import org.mts.notificationservice.utilities.WebUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("notifications")
public class NotificationController {

    private final INotificationService notificationService;
    private final ModelMapper modelMapper;
    private final SimpMessageSendingOperations sendingOperations;

    public NotificationController(INotificationService notificationService, ModelMapper modelMapper, SimpMessageSendingOperations sendingOperations) {
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
        this.sendingOperations = sendingOperations;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public List<Message> getMessages(Authentication authentication){
        return this.notificationService.findAllByUserId(authentication.getName()).stream().map((element) -> this.modelMapper.map(element, Message.class))
                .toList();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('USER')")
    public Map<String, Object> getMessagesList(
            Authentication authentication,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String sortField,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(required = false) String search) {

        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isBlank()) {
            sort = "desc".equalsIgnoreCase(sortOrder)
                    ? Sort.by(sortField).descending()
                    : Sort.by(sortField).ascending();
        }

        Page<Notification> messages = this.notificationService.getMessages(authentication.getName(), PageRequest.of(page, size, sort), search);
        Map<String, Object> map = WebUtils.pageToMap(messages);
        map.put("content", messages.getContent().stream().map(c -> {
            return this.modelMapper.map(c, Message.class);
        }).toList());
        return map;
    }


    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/sendMessageTo/{userId}")
    public void sendMessageTo(@Payload Message message, @PathVariable String userId) {
       this.notificationService.notifyUser(userId, "/queue", this.modelMapper.map(message, Notification.class));
    }


    @DeleteMapping("/{id}")
    public Message delete(@PathVariable Long id){
        return this.modelMapper.map(this.notificationService.deleteById(id), Message.class);
    }

    @MessageMapping("/markAsSeen/{id}")
    public void markAsRead(@DestinationVariable Long id, Principal principal) {
        Notification notif = this.notificationService.findByIdAndUserId(id, principal.getName());
        notif.setSeen(true);
        this.notificationService.save(notif);
    }

    @MessageMapping("/newConnection")
    public void newConnection(Principal principal) {
        List<Notification> notifications = this.notificationService.findByUserIdAndSeenFalse(principal.getName());
        for (Notification notif : notifications) {
            this.sendingOperations.convertAndSendToUser(principal.getName(), "/queue", this.modelMapper.map(notif, Message.class));
        }
    }


}
