package com.bangnc.identity_service.mapper;

import com.bangnc.identity_service.dto.request.PermissionRequest;
import com.bangnc.identity_service.dto.request.UserCreationRequest;
import com.bangnc.identity_service.dto.response.PermissionResponse;
import com.bangnc.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission user);

}
