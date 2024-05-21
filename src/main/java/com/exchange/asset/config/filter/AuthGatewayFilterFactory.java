package com.exchange.asset.config.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
  private final AuthGatewayFilter authGatewayFilter;

  @Override
  public GatewayFilter apply(Object config) {
    return authGatewayFilter;
  }

  @Override
  public String name() {
    return "AuthGatewayFilterFactory";
  }
}
