# External gateway

### Content
* [How to use](#how-to-use)
* [I18n support](#i18n-support)
* [Nacos env vars](#nacos-env-vars)
* [Links](#links)

### How to use
This service serve as gateway or proxy to all other services that are hidden behind VPC. To access services some requirements should be met, for example authentication, and it's better to have a single gateway in front of other services through which all the requests would go, and it would act as proxy that would validate auth and forward request to specified service. There are 2 types of endpoints:
* public - you don't need to any auth to access them, this is `/info/version` API endpoint in each microservice
* private - you have to pass auth token to access them (here you can pass anything you want including HMAC signature and JWT, look into `AuthServiceImpl`)
Public endpoints:
```shell
# call version API for asset-service
curl http://127.0.0.1:8083/asset-service/info/version
# call version API for order-service
curl http://127.0.0.1:8083/order-service/info/version
```
Private endpoints:
```shell
# add new asset
curl -H 'X-API-KEY: admin' -H 'X-API-SIGNATURE: admin' -H 'content-type: application/json' -d '{"symbol":"BTC","price":100}' http://localhost:8083/asset-service/asset/price
# get asset price
curl -H 'X-API-KEY: admin' -H 'X-API-SIGNATURE: admin' -H 'content-type: application/json' http://localhost:8083/asset-service/asset/price/BTC
# create new order
curl -H 'X-API-KEY: admin' -H 'X-API-SIGNATURE: admin' -H 'content-type: application/json' -d '{"symbol":"BTC","quantity":5}' http://localhost:8083/order-service/order
# get order by symbol
curl -H 'X-API-KEY: admin' -H 'X-API-SIGNATURE: admin' -H 'content-type: application/json' http://localhost:8083/order-service/order/BTC
```
Here is example with i18n
```shell
# call asset-service to get price for non-existing coin ABC
curl -H 'X-API-KEY: admin' -H 'X-API-SIGNATURE: admin' -H 'Accept-Language: es' -H 'content-type: application/json' http://localhost:8083/asset-service/asset/price/ABC
# since there is no such price for ABC, asset-service will return 400 with error in Spanish
{"code":100001,"errorCode":"price_not_found","msg":"No se pudo obtener el precio de ABC","traceId":"664c52e4c60e74602dc96f112d1975f8"}
```

### Nacos env vars
Make sure your nacos server is running and you pass it's IP into config variables. If you try to run
this app without nacos config then it won't start.
```
NACOS_SERVER_IP=127.0.0.1;
NACOS_SERVER_ID=31b66f4e-dbf4-4745-a359-2d9701f436e5;
```

### Links
This project is a part of spring cloud microservices that includes following projects:
* [asset-service](https://github.com/dgaydukov/spring-cloud-asset-service) - provide asset price information
* [order-service](https://github.com/dgaydukov/spring-cloud-order-service) - calls asset service to get the price
* [external-gateway](https://github.com/dgaydukov/spring-cloud-external-gateway) - serve as gateway to forward requests from customers. You can call above 2 services from this gateway.
* [cloud project docs](https://github.com/dgaydukov/spring-cloud-project?tab=readme-ov-file) - special repository with all documentation for intra-service communication