package com.exchange.asset.service.impl;

import com.exchange.asset.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

  @Override
  public boolean authByApiKey(String apiKey, String sign) {
    return true;
  }

  @Override
  public int getUserIdByApiKey(String apiKey) {
    return 1;
  }
}
