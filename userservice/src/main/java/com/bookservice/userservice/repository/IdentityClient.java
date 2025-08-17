package com.bookservice.userservice.repository;

import com.bookservice.userservice.dto.identity.TokenExchangeParam;
import com.bookservice.userservice.dto.identity.TokenExchangeRepsonse;
import com.bookservice.userservice.dto.identity.UserCreationParam;
import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(
            value = "/realms/master/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    TokenExchangeRepsonse exchangeClientToken(@QueryMap() TokenExchangeParam param);

    @PostMapping(
            value = "admin/realms/master/users",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> createUser(@RequestBody() UserCreationParam body, @RequestHeader("authorization") String token);
}
