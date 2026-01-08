package com.substring.chat.coresdk;



import com.substring.chat.coresdk.dto.GetUserDeviceInfoDTO;
import com.substring.chat.coresdk.dto.UserRolePermissionResponse;

import java.util.List;

public interface IUserServiceSDK {


    UserRolePermissionResponse getUserByEmailIgnoreCaseAndIsActive(String email);

    List<GetUserDeviceInfoDTO> getDeviceInfoByUserId(String userId);

    UserRolePermissionResponse getByUserId(String userId);

    List<UserRolePermissionResponse> getAllUserByIds(List<String> userIds);
}
