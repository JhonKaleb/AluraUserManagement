package com.alura.UserManagement.domain.user;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class UserRoleDeserializer extends JsonDeserializer<UserRole> {
    @Override
    public UserRole deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String code = jsonParser.getText().toUpperCase();
        return UserRole.getByCd(code);
    }
}
