package com.bms.enums;

import com.bms.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum UserType {
    CUSTOMER("customer"),
    ADMIN("admin"),
    BOXOFFICE("boxoffice");

    private String name;
    private static HashMap<String, UserType> byName;
    static{
        byName = new HashMap<String, UserType>();
        for(UserType type: values()){
            if(type.getName()!=null){
                byName.put(type.getName(), type);
            }
        }
    }

    public static UserType getEnum(String type){
        return byName.get(type);
    }
}