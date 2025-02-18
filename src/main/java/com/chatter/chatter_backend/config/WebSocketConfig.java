package com.chatter.chatter_backend.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry config){


       config.enableSimpleBroker("/topic");
       //this enables a simple in-memory message broker.
       //The message broker is responsible for routing messages from one client to another.
       //Any destination prefixed with /topic will be handled by this broker.
       //Example Use Case: When clients subscribe to a topic like /topic/messages, they will receive broadcast messages sent to this topic.

       config.setApplicationDestinationPrefixes("/app");
      //The setApplicationDestinationPrefixes("/app") method in Spring WebSocket configuration 
      //is used to define a prefix for destinations that clients send messages to, 
      //which will then be routed to @MessageMapping annotated methods in your controllers.
      //For instance, if the client sends a message to /app/chat, it maps to

      //   @MessageMapping("/chat")
      // public void handleChatMessage(Message message) {
      //     ** Process the message**
      // }

    }
 
    @Override
    public  void registerStompEndpoints(@NonNull StompEndpointRegistry registry){

            registry.addEndpoint("/chat")
                    .setAllowedOrigins(AppConstants.FRONT_END_BASE_URL)
                    .withSockJS();

    }
    
}
