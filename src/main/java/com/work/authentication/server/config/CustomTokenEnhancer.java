package com.work.authentication.server.config;

import com.work.authentication.server.domain.MainUsers;
import com.work.authentication.server.utils.TokenConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/*this class add the necessary details to be embeded in the token*/
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        Authentication authentication = oAuth2Authentication.getUserAuthentication();

        if(authentication != null && authentication.getDetails() instanceof MainUsers){
            MainUsers mainUsers = (MainUsers) authentication.getDetails();

            additionalInfo.put(TokenConstant.CLAIM_CLIENT_ID, mainUsers.getId());
            additionalInfo.put(TokenConstant.CLAIM_FIRST_NAME, mainUsers.getFirstName());
            additionalInfo.put(TokenConstant.CLAIM_USERNAME, mainUsers.getUsername());
            additionalInfo.put(TokenConstant.CLAIM_LAST_NAME, mainUsers.getLastName());
            additionalInfo.put(TokenConstant.ROLE_DESCRIPTION, mainUsers.getRoleDescription());
        }

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);

        return oAuth2AccessToken;
    }
}
