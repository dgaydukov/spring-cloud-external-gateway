package com.exchange.asset.service.impl;

import com.exchange.asset.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

  /**
   * You can rewrite this code to validate whatever you want including
   * HMAC or JWT
   */
  @Override
  public boolean authByApiKey(String apiKey, String sign) {
    final String ADMIN = "admin";
    if (ADMIN.equals(apiKey) && ADMIN.equals(sign)){
      return true;
    }
    return false;
  }

  @Override
  public int getUserIdByApiKey(String apiKey) {
    return 1;
  }
}
