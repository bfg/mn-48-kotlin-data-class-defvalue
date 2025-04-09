# micronaut 4.8.x data-class default value issue in @ConfigurationProperties

This project reproduces the issue in micronaut 4.8.x where kotlin `data class` property with default value doesn't
get assigned if it's missing in config leading to exception.

## To reproduce

```

# run the server (4.7.6)
❯ ./gradlew run

# call the controller
❯ curl -s http://localhost:8080/v1/my-controller | jq .
{
  "enabled": true,
  "apiBase": "https://api.example.com/",
  "something": "trololo-something-value"
}

# replace micronautVersion to 4.8.0 in build.gradle
# run the server (4.8.x)
❯ ./gradlew run

# call the controller
❯ curl -s http://localhost:8080/v1/my-controller | jq .
{
  "type": "about:blank",
  "status": 500
}
```

server exception:
```
[2025/04/09 13:00:33.052] [default-nioEventLoopGroup-1-2] ERROR i.m.h.s.RouteExecutor: [] Unexpected error occurred: Failed to inject value for parameter [apiBase] of class: com.github.bfg.mn48.ControllerCfg
> :run
Path Taken:
new c.g.b.m.Controller(ControllerCfg cfg)
\---> new c.g.b.m.Controller([ControllerCfg cfg])
      \---> new @i.m.c.a.ConfigurationProperties("app.controller") c.g.b.m.ControllerCfg(boolean enabled, [String apiBase], String something)
io.micronaut.context.exceptions.DependencyInjectionException: Failed to inject value for parameter [apiBase] of class: com.github.bfg.mn48.ControllerCfg

Path Taken:
new c.g.b.m.Controller(ControllerCfg cfg)
\---> new c.g.b.m.Controller([ControllerCfg cfg])
      \---> new @i.m.c.a.ConfigurationProperties("app.controller") c.g.b.m.ControllerCfg(boolean enabled, [String apiBase], String something)
        at io.micronaut.context.AbstractInitializableBeanDefinition.getPropertyValueForConstructorArgument(AbstractInitializableBeanDefinition.java:1431)
        at com.github.bfg.mn48.$ControllerCfg$Definition.instantiate(Unknown Source)

...

Caused by: io.micronaut.context.exceptions.BeanInstantiationException: Error instantiating bean of type  [com.github.bfg.mn48.ControllerCfg]

Message: Validation failed for bean definition [com.github.bfg.mn48.ControllerCfg]
List of constraint violations:[
        com.github.bfg.mn48.ControllerCfg.apiBase - must not be blank
]
Path Taken:
new c.g.b.m.Controller(ControllerCfg cfg)
\---> new c.g.b.m.Controller([ControllerCfg cfg])
      \---> new @i.m.c.a.ConfigurationProperties("app.controller") c.g.b.m.ControllerCfg(boolean enabled, [String apiBase], String something)
        at io.micronaut.validation.validator.DefaultValidator.failOnError(DefaultValidator.java:1540)
        at io.micronaut.validation.validator.DefaultValidator.validateBeanArgument(DefaultValidator.java:711)
        at io.micronaut.inject.ValidatedBeanDefinition.validateBeanArgument(ValidatedBeanDefinition.java:66)
        at io.micronaut.context.AbstractInitializableBeanDefinition.getPropertyValueForConstructorArgument(AbstractInitializableBeanDefinition.java:1420)
        ... 67 common frames omitted
<
```
