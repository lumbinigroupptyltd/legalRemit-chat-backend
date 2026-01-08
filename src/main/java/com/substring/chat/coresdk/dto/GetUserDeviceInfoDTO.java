package com.substring.chat.coresdk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDeviceInfoDTO {
    private String id;
    private String client;
    private String model;
    private String osVersion;
    private String appVersion;
    private String ip;
    private String host;
    private String deviceId;
    private String token;
    private String refToken;
    private String fcmToken;
    //    private Boolean isFromSignup = Boolean.FALSE;
    //    private String countryId;
    private String timeStamp;
    private Boolean isFromSignup = Boolean.FALSE;
    private String userId;
    private String countryId;

}
