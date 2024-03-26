package com.alura.UserManagement.domain.course;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CourseStatusDeserializer extends JsonDeserializer<CourseStatus> {
    @Override
    public CourseStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String code = jsonParser.getText().toUpperCase();
        return CourseStatus.getByCd(code);
    }
}
