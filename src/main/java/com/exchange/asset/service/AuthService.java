package com.exchange.asset.service;

public interface AuthService {
  boolean authByApiKey(String apiKey, String sign);
  int getUserIdByApiKey(String apiKey);
}
