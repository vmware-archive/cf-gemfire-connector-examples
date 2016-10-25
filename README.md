# cf-gemfire-connector-examples

Example applications that use the GemFire on Pivotal Cloud Foundry service.

For an application to connect to a provisioned GemFire cluster in Cloud Foundry, it has to create a GemFire ClientCache which connects to the GemFire locators. The application also has to provide an implementation of AuthInitialize which will send the client credentials to the server. The credentials and the locator information is present in the VCAP_SERVICES environment variable. This repository has five examples of how these environment variables can be parsed.

There are 5 example applications in this repository:
- Java main application
- Java main application using spring cloud connectors
- Spring boot application using spring cloud connectors
- Spring boot application using spring cloud connectors with custom GemFire client configuration
- Spring boot application using spring cloud connectors and GemfireRepository

Following are the common steps for all examples to create a GemFire Service and create a Region:
- create a GemFire service instance named "service0"
  `cf create-service p-gemfire GemFireServicePlan1 service0`
- install the gemfire plugin for the `cf` command
- create a region named "test"
  - connect `gfsh` to the service instance
    `cf show-gfsh service0` will print the command to run in gfsh to connect to the cluster (install GemFire cf plugin from [Pivotal Network](http://network.pivotal.io)
  - `gfsh>create region --name=test --type=PARTITION` will create a region named "test"

Clone this repo, then build all examples with:
```
$ ./gradlew build
```

To deploy individual application to cloudfoundry:
1. cd to the project dir
1. `$ cf push`

## Java main application

application name: `java-app`

This application uses jackson to parse the environment variable in the EnvParser class. ClientAuthInitialize is an implementation of AuthInitialize that is used for handling client authenticatin. The main class, MyJavaApplication creates a ClientCache that uses the ClientAuthInitialize for authentication and uses EnvParser to get the locator information.

## Java main application using spring cloud connector

application name: `java-app-spring-cloud`

To use the spring connector, your application does *not* have to be spring application. Just specify spring-cloud-core and spring-cloud-gemfire-cloudfoundry-connector as your project dependencies and let the connector parse the VCAP_SERVICES environment variable for you. In the application you can get ServiceInfo like so:

```
    CloudFactory cloudFactory = new CloudFactory();
    Cloud cloud = cloudFactory.getCloud();
    GemfireServiceInfo myService = (GemfireServiceInfo) cloud.getServiceInfo("service0");
```
You will still need to provide an implementation of AuthInitialize like in the previous example. Please refer to the application.

## Spring Application

application name: `spring-app`

If you use Spring, you can just `Autowire` ClientCache into your application.

## Spring Application with custom GemFire client configuration

application name: `spring-app-client-config`

If the ClientCache requires additional configuration then the Cache needs to be explictly created using a `ServiceConnectorConfig`. The application needs to create a `CacheClient` bean using the CloudFactory.

## Spring Application using Spring GemfireRepository

application name: `gemfire-spring-pizza-store`

This is a GemFire Spring client application which uses GemfireRepository class to access a GemFire cluster. The application assumes that a GemFire cluster exists and contains a region named __Pizza__. Please see instructions above on how to create a GemFire cluster.

To create a Region on the GemFire servers, connect to the cluster using `gfsh` and enter the command `create region --name=Pizza --type=PARTITION`. Once you have the region created, please deploy the App using `cf push`. The application registers a http endpoint `/healthcheck` which inserts and reads a Pizza object from the GemFire cluster.