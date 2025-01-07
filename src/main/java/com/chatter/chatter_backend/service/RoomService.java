 package com.chatter.chatter_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatter.chatter_backend.entities.Room;
import com.chatter.chatter_backend.repositories.RoomRepository;

@Service
public class RoomService {

  @Autowired
  RoomRepository roomRepository;
   
  public Room findByRoomId(String roomId){
   return roomRepository.findByRoomId(roomId);
  }

  public Room save(Room room){
    return roomRepository.save(room);
  }

}
