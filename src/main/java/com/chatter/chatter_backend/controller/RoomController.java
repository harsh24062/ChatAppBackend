package com.chatter.chatter_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chatter.chatter_backend.entities.Message;
import com.chatter.chatter_backend.entities.Room;
import com.chatter.chatter_backend.service.RoomService;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("https://localhost:3000")
public class RoomController {

    @Autowired
    private RoomService roomService;

    //create room
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody String roomId){

        Room room=roomService.findByRoomId(roomId);

        if(room!=null){
            return ResponseEntity.badRequest().body("Room already exists!!!");
        }

        Room newRoom=new Room();
        newRoom.setRoomId(roomId);
        Room savedRoom=roomService.save(newRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);  
    }


    //get room (user trying to access room)
     @GetMapping("/{roomId}")
     public ResponseEntity<?> joinRoom(@PathVariable String roomId){

        Room isRoomExist=roomService.findByRoomId(roomId);

        // Room dont exist
        if(isRoomExist==null) return ResponseEntity.badRequest().body("Room not found!!!");
        return ResponseEntity.ok(isRoomExist);
     }


    //get messages of room
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(
        @PathVariable("roomId") String roomId,
        @RequestParam(value = "page",defaultValue = "0",required = false) int page,
        @RequestParam(value = "size",defaultValue = "20",required = false) int size
        ){

        Room room=roomService.findByRoomId(roomId);

        // no room means  no messages
        if(room==null)return ResponseEntity.badRequest().build(); 
        
        // messages
        List<Message> messages=room.getMessages();

        //pagination
       int start=Math.max(0,messages.size() - (page+1)*size);
       int end=Math.min(messages.size(),start+size);
       List<Message> paginatedMessages=messages.subList(start, end);

       return ResponseEntity.ok(paginatedMessages);
    }
}
