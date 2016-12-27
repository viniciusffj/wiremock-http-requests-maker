# wiremock-http-requests-maker
Wiremock extension to make http request.

# Motivation
When creating a mock for a service, we usually just need to simulate response for some request.
But sometimes the service being mocked demands more than that, such as notifying another service.

This wiremock extension makes it possible to call another service from wiremock.

# Usage

## Wiremock standalone process

### Configuring wiremock

Download the [wiremock jar](http://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/2.4.1/wiremock-standalone-2.4.1.jar).

Download the extension jar.

Start wiremock by running:
```
java -cp "wiremock-http-requests-maker.jar:wiremock-standalone.jar" com.github.tomakehurst.wiremock.standalone.WireMockServerRunner --verbose --extensions com.github.viniciusffj.wiremock.HttpRequestMaker
```

Please, read more about running wiremock as a standalone process in [their documentation](http://wiremock.org/docs/running-standalone/).
 
### Using the extension

First of all, we need to tell wiremock we are using the extension by doing:
```
... "transformers": ["http-request-maker"] ...
}
```

Then we need to inform the parameters to the extension:
```
{
...
    "response": {
        ...
        "transformerParameters" : {
            "http_request_maker" : {
              "url": "http://my.api/users",
              "method": "POST",
              "body": "{\"name\": \"Willian\"}",
              "headers": {
                "Content-Type": "application/json"
              }
            }
        },
        ...
    }
}
```

The complete example:

```
{
    "request": {
        "method": "GET",
        "url": "/hello"
    },
    "response": {
        "status": 200,
        "body": "Hello world!",
        "headers": {
          "Content-Type": "text/plain"
        },
        "transformerParameters" : {
          "http_request_maker" : {
            "url": "http://my.api/users",
            "method": "POST",
            "body": "{\"name\": \"Willian\"}",
            "headers": {
              "Content-Type": "application/json"
            }
          }
        },
        "transformers": ["http-request-maker"]
    }
}
```

Only `url` and `method` are mandatory parameters.

# Development

To run the tests use:
```
./gradlew test
```

To run the mutation tests using [PIT Test](http://pitest.org/):
```
./gradlew pitest
```

To generate a jar, just run:
```
./gradlew jar
```

And the jar will be in directory `build/libs/`.