package com.api.csm.interfaces.user;


import com.api.csm.dto.user.UserDetailDto;
import com.api.csm.dto.user.UserUpdateDto;
import com.api.csm.models.User;
import org.mapstruct.*;

import java.util.UUID;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id",expression = "java(user.getId())")
    @Mapping(target = "email")
    @Mapping(target = "fullname")
    UserDetailDto toUserDetailDto(User user);


    default UUID parseUUID(String value) {
        try {
            return value != null ? UUID.fromString(value) : null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget User entity);
}
