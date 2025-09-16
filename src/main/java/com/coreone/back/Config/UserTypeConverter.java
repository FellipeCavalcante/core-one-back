package com.coreone.back.Config;

import com.coreone.back.domain.enums.UserType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType userType) {
        if (userType == null) {
            return null;
        }
        return userType.name().toLowerCase();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return UserType.valueOf(dbData.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}