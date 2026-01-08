package com.substring.chat.coresdk;



import com.substring.chat.coresdk.dto.GetUserDeviceInfoDTO;
import com.substring.chat.coresdk.dto.ListOfIdsDTO;
import com.substring.chat.coresdk.dto.ObjectResponse;
import com.substring.chat.coresdk.dto.UserRolePermissionResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class IUserServiceSDKImpl implements IUserServiceSDK {
    private final WebClientSDK webClient;
    @Value("${base_core_api_url}")
    private String baseUrl;
    private final ModelMapper modelMapper;

    public IUserServiceSDKImpl(WebClientSDK webClient, ModelMapper modelMapper) {
        this.webClient = webClient;
        this.modelMapper = modelMapper;
    }


    @Override
    public UserRolePermissionResponse getUserByEmailIgnoreCaseAndIsActive(String email) {
        String url = baseUrl + "/user/getUserByEmail?email=" + email;
        log.info("Get By Email URL:{}", url);
        ObjectResponse response = webClient.getForEntityDynamic(url, null, ObjectResponse.class, true);
        return modelMapper.map(response != null ? response.getData() : null, UserRolePermissionResponse.class);
    }

    @Override
    public List<GetUserDeviceInfoDTO> getDeviceInfoByUserId(String userId) {
        String url = baseUrl + "/userdeviceinfo/getbyuserid/" + userId;
        log.info("Get By UserId URL:{}", url);
        ObjectResponse response = webClient.getForEntityDynamic(url, null, ObjectResponse.class, true);
        return modelMapper.map(Objects.requireNonNull(response).getData(), new TypeToken<List<GetUserDeviceInfoDTO>>() {
        }.getType());
    }

    @Override
    public UserRolePermissionResponse getByUserId(String userId) {
        String url = baseUrl + "/user/getByIdV2/" + userId;
        log.info("Get By UserId URL:{}", url);
        ObjectResponse response = webClient.getForEntityDynamic(url, null, ObjectResponse.class, true);
        return modelMapper.map(Objects.requireNonNull(response).getData(), UserRolePermissionResponse.class);
    }

    @Override
    public List<UserRolePermissionResponse> getAllUserByIds(List<String> userIds) {

        if (userIds == null || userIds.isEmpty()) {
            return List.of();
        }

        try {
            String url = baseUrl + "/user/getAllByIds";
            ListOfIdsDTO body = new ListOfIdsDTO(userIds);

            return webClient.postForEntityDynamic(
                    url,
                    body,
                    new ParameterizedTypeReference<List<UserRolePermissionResponse>>() {
                    },
                    false
            );

        } catch (Exception e) {
            log.error("Failed to fetch users by ids", e);
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

}