package com.chatter.chatter_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.chatter.chatter_backend.entities.Message;
import com.chatter.chatter_backend.entities.Room;
import com.chatter.chatter_backend.payload.MessageRequest;
import com.chatter.chatter_backend.service.RoomService;

@Controller
@CrossOrigin("http://localhost:5173")
public class ChatController {

    @Autowired
    private RoomService roomService;

    //for sending and receiving messages

   // @DestinationVariable works similarly to @PathVariable in REST controllers.
   //The @DestinationVariable annotation tells Spring to extract the dynamic portion 
   //of the destination ({roomId} in this case) and bind it to the roomId parameter in the method.

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
        @DestinationVariable String roomId,    
        @RequestBody MessageRequest request
    ) throws Exception{

        Room room=roomService.findByRoomId(request.getRoomId());

        Message message=new Message(request.getSender(),request.getContent());

        if(room!=null){
            room.getMessages().add(message);
            roomService.save(room);
        }
        else throw new RuntimeException("room not found");
        
          return message; 
    }


}
