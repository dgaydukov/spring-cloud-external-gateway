package com.exchange.asset.service.impl;

import com.exchange.asset.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This is example implementation with hardcoded value and userId
 * In practice you can rewrite this class to either: fetch data from db, make HMAC signature verification
 * or validate JWT token, all can be done here
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {


  @Override
  public boolean authByApiKey(String apiKey, String sign) {
    final String ADMIN = "admin";
    if (ADMIN.equals(apiKey) && ADMIN.equals(sign)) {
      return true;
    }
    return false;
  }

  @Override
  public int getUserIdByApiKey(String apiKey) {
    return 1;
  }
}
