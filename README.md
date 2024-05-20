# External gateway

### How to use
This service serve as gateway or proxy to all other services that are hidden behind VPC. To access services some requirements should be met, for example authentication, and it's better to have a single gateway in front of other services through which all the requests would go, and it would act as proxy that would validate auth and forward request to specified service. There are 2 types of endpoints:
* public - you don't need to any auth to access them, this is `/info/version` API endpoint in each microservice
* private - you have to pass auth token to access them
```shell
# call version API for asset-service
curl http://127.0.0.1:8083/asset-service/info/version
# call version API for order-service
```

### Nacos config
Make sure your nacos server is running and you pass it's IP into config variables. If you try to run
this app without nacos config then it won't start.
```
NACOS_SERVER_IP=127.0.0.1;
NACOS_SERVER_ID=31b66f4e-dbf4-4745-a359-2d9701f436e5;
```