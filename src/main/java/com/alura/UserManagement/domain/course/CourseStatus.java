package com.alura.UserManagement.domain.course;

import com.alura.UserManagement.utils.Code;
import com.alura.UserManagement.utils.EnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = CourseStatusDeserializer.class)
public enum CourseStatus implements Serializable, Code<CourseStatus> {
    ACTIVE("ATIVO"),
    INACTIVE("INATIVO");

    private final String code;

    public static CourseStatus getByCd(String cd) {
        return EnumConverter.getByCode(cd, values());
    }

    public static class Converter implements AttributeConverter<CourseStatus, String> {

        @Override
        public String convertToDatabaseColumn(CourseStatus status) {
            if (status == null) return null;
            return status.getCode();
        }

        @Override
        public CourseStatus convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return getByCd(dbData);
        }
    }
}
