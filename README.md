# Quarkus login :man_with_turban:

Simple quarkus login microservice. It will return a `200` if user password are correct `401` otherwise.

## Run the aplication

To run the application just `mvn quarkus:dev`. It will deply it in `http:0.0.0.0:8080`. 

There are three users with different roles:
|user|pass|roles|
|---|---|---|
|root|toor|root, admin, user|
|admin|admin|admin, user|
|ramon|pradom|user

The following requests should responde with a `200`:
```bash
curl -v --user root:toor http://localhost:8080/login/authentication
curl -v --user admin:admin http://localhost:8080/login/authentication
curl -v --user ramon:pradom http://localhost:8080/login/authentication
```
Response:
```bash
*   Trying ::1:8080...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
* Server auth using Basic with user 'root'
> GET /login/authentication HTTP/1.1
> Host: localhost:8080
> Authorization: Basic cm9vdDpkZnRvb3I=
> User-Agent: curl/7.66.0
> Accept: */*
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 401 Unauthorized
* Authentication problem. Ignoring this.
< www-authenticate: basic realm="Quarkus"
< content-length: 0
< 
* Connection #0 to host localhost left intact
```
Any other request:
```bash
*   Trying ::1:8080...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
* Server auth using Basic with user 'root'
> GET /login/authentication HTTP/1.1
> Host: localhost:8080
> Authorization: Basic cm9vdDpkZnRvb3I=
> User-Agent: curl/7.66.0
> Accept: */*
> 
* Mark bundle as not supporting multiuse
< HTTP/1.1 401 Unauthorized
* Authentication problem. Ignoring this.
< www-authenticate: basic realm="Quarkus"
< content-length: 0
< 
* Connection #0 to host localhost left intact
```
### Health check
The app has some health checks you can consult:
* `curl -v http://localhost:8080/health`
  * ```bash
    *   Trying ::1:8080...
    * TCP_NODELAY set
    * Connected to localhost (::1) port 8080 (#0)
    > GET /health HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.66.0
    > Accept: */*
    > 
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 503 Service Unavailable
    < content-type: application/json; charset=UTF-8
    < content-length: 238
    < 

    {
        "status": "DOWN",
        "checks": [
            {
                "name": "Health check done",
                "status": "UP"
            },
            {
                "name": "Database connection (mocked)",
                "status": "DOWN"
            }
        ]
    * Connection #0 to host localhost left intact
    }
    ``` 
* ` curl -v http://localhost:8080/health/live`
  * ```bash
    *   Trying ::1:8080...
    * TCP_NODELAY set
    * Connected to localhost (::1) port 8080 (#0)
    > GET /health/live HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.66.0
    > Accept: */*
    > 
    * Mark bundle as not supporting multiuse
    < HTTP/1.1 200 OK
    < content-type: application/json; charset=UTF-8
    < content-length: 134
    < 

    {
        "status": "UP",
        "checks": [
            {
                "name": "Health check done",
                "status": "UP"
            }
        ]
    * Connection #0 to host localhost left intact
    }
    ```
* `curl -v http://localhost:8080/health/ready`
   * ```bash
        *   Trying ::1:8080...
        * TCP_NODELAY set
        * Connected to localhost (::1) port 8080 (#0)
        > GET /health/ready HTTP/1.1
        > Host: localhost:8080
        > User-Agent: curl/7.66.0
        > Accept: */*
        > 
        * Mark bundle as not supporting multiuse
        < HTTP/1.1 503 Service Unavailable
        < content-type: application/json; charset=UTF-8
        < content-length: 149
        < 

        {
            "status": "DOWN",
            "checks": [
                {
                    "name": "Database connection (mocked)",
                    "status": "DOWN"
                }
            ]
        * Connection #0 to host localhost left intact
        }
      ```
## Compile

### Java executable
* `mvn package -DskipTests`
* `java -jar target/*-runner.jar`
### Native executable
* `mvn package -Pnative -DskipTests`
* `./target/*-runner`

**Note**: You need [GraalVm](https://www.graalvm.org/) in order to produce a native executable.
### Create docker :whale: native container
 * Create the native image 
   * `mvn package -Pnative -Dquarkus.native.container-runtime=docker -DskipTests`
 * `docker build -f src/main/docker/Dockerfile.native -t quarkus-login .`
 * `docker run -i --rm -p 8080:8080 quarkus-quickstart/quarkus-login`

:wave: :wave: