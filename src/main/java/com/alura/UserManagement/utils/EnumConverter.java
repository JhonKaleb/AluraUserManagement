package com.alura.UserManagement.utils;

/**
 * Generically converts our code enum pattern from String codes to Enum values
 */
public class EnumConverter {

    public static <T extends Code> T getByCode(String code, T[] values){

        if(!ParserUtil.nonNullNonEmpty(code)){
            return null;
        }

        for(T val:values){
            if(code.equals(val.getCode())){
                return val;
            }
        }

        return null;
    }
}
