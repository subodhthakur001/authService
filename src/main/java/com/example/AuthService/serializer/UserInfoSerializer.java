package com.example.AuthService.serializer;

import com.example.AuthService.model.UserInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.io.OutputStream;
import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoDto> {


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String arg0, UserInfoDto userInfoDto) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
             retVal = objectMapper.writeValueAsString(userInfoDto).getBytes();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return retVal;
     }

    @Override
    public byte[] serialize(String topic, Headers headers, UserInfoDto data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
