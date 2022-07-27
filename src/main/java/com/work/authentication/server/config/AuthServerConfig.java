package com.work.authentication.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    @Qualifier("customTokenEnhancer")
    TokenEnhancer tokenEnhancer;
    @Autowired
    CurrentUsersDetails currentUsersDetails;
    @Autowired
    @Qualifier("customAuthenticationManagerBean")
    AuthenticationManager authenticationManager;
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    KeyStoreConfig keyStoreConfig;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer){
        oauthServer.tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, accessTokenConverter()));
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setKeyPair(keyStoreKeyFactory().getKeyPair(keyStoreConfig.getAlias()));
        return converter;
    }

    @Bean
    public KeyStoreKeyFactory keyStoreKeyFactory(){
        return new KeyStoreKeyFactory(resourceLoader.getResource(keyStoreConfig.getLocation()),
                keyStoreConfig.getPassword().toCharArray());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);

        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
}
