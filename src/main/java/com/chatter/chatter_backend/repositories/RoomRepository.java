package com.chatter.chatter_backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatter.chatter_backend.entities.Room;

public interface RoomRepository extends MongoRepository<Room,String> {
    Room  findByRoomId(String roomId);  
}
