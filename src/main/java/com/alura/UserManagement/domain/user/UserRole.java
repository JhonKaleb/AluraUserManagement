package com.alura.UserManagement.domain.user;

import com.alura.UserManagement.utils.Code;
import com.alura.UserManagement.utils.EnumConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.AttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@JsonDeserialize(using = UserRoleDeserializer.class)
public enum UserRole implements GrantedAuthority, Serializable, Code<UserRole> {
    STUDENT("ESTUDANTE"),
    INSTRUCTOR("INSTRUTOR"),
    ADMIN("ADMIN");

    private final String code;

    @Override
    public String getAuthority() {
        return "ROLE_" + code;
    }

    public static UserRole getByCd(String cd) {
        return EnumConverter.getByCode(cd, values());
    }

    public static class Converter implements AttributeConverter<UserRole, String> {

        @Override
        public String convertToDatabaseColumn(UserRole code) {
            return code.getCode();
        }

        @Override
        public UserRole convertToEntityAttribute(String s) {
            return getByCd(s);
        }
    }
}