# Rest API - examples

Rest API application allow to get list of vehicles from localization by distance in metres. Also allow to upload and download files.

## Examples

Valid and unvalid requests on important endpoints.

### Authorization

These endpoints allow users to get authentication token or check information about token.

#### Get access token - valid request

Request:
```
$ curl 'http://localhost:8080/oauth/token' -i -u 'exampleClientID:exampleSecret' -X POST \
    -H 'Accept: application/json;charset=UTF-8' \
    -d 'grant_type=password&username=John&password=123'
```
Response:
```
{
   "access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyNDg2ODQsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiUkhveXp3eHJVODFxbC13cjVhN0hvb2dlam1rIiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.DIHqxTh1swztGhp7ztsDFgoVP0EHqIndfxAAMFxL9es",
   "token_type":"bearer",
   "expires_in":599,
   "scope":"read write",
   "jti":"RHoyzwxrU81ql-wr5a7Hoogejmk"
}
```

#### Get access token - invalid request

Request:
```
$ curl 'http://localhost:8080/oauth/token' -i -u 'exampleClientID:exampleSecret' -X POST \
    -H 'Accept: application/json;charset=UTF-8' \
    -d 'grant_type=password&username=InvalidLogin&password=InvalidPassword'
```

Response:
```
{
   "error":"invalid_grant",
   "error_description":"Bad credentials"
}
```

#### Check access token - valid request

Request:
```
$ curl 'http://localhost:8080/oauth/check_token' -i -u 'exampleClientID:exampleSecret' -X POST \
    -d 'token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyNDg2ODMsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiN0ViSUR3YXNDVXUyZ3JQWDhxWU9SWjJlalRzIiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.GwaFezY5Jhq_R0FVWZJUU4CTJGcA-aU8fLYFxqVUEeU'
```

Response:
```
{
   "user_name":"John",
   "scope":[
      "read",
      "write"
   ],
   "active":true,
   "exp":1601248683,
   "authorities":[
      "ROLE_USER"
   ],
   "jti":"7EbIDwasCUu2grPX8qYORZ2ejTs",
   "client_id":"exampleClientID"
}
```

### Vehicles

This endpoint will return a list of vehicles that are in specific distance from given localization.

#### Get vehicles list - with auth

Request:
```
$ curl 'http://localhost:8080/vehicles?lat=53.9037654770889&lon=20.887423009119&dist=50.0' -i -X GET \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyNDg2ODQsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoic2lMUFgyWE1MRVg1WE1pUl8tVW9Gc2FTdFVzIiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.6YfFYnxro2Ug1CWNob1kuoImltD10U6ffsN7ZolahQM'
```

Response:
```
{
   "amount":2,
   "vehicles":[
      {
         "position_id":"8e0b1e1a-e290-4344-8fa1-39de81c9d265",
         "latitude":53.9037654770889,
         "longitude":20.887423009119
      },
      {
         "position_id":"b8e77849-e8c3-4642-bd64-07abf2e94163",
         "latitude":53.9033883013165,
         "longitude":20.8876412703406
      }
   ]
}
```

#### Get vehicles list - without auth

Request:
```
$ curl 'http://localhost:8080/vehicles?lat=53.9037654770889&lon=20.887423009119&dist=50.0' -i -X GET

```

Response:
```
{
   "error":"unauthorized",
   "error_description":"Full authentication is required to access this resource"
}
```

### Files

These endpoints allow user to upload and download files on server.

#### Upload file - with auth

Request:
```
$ curl 'http://localhost:8080/uploadFile' -i -X POST \
    -H 'Content-Type: multipart/form-data;charset=UTF-8' \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyNDg2ODQsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiWU5RMjFnV3hvalcwbFdRSnRPQ1kwbzhadWpjIiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.bKMaEDBMMYUEppR5nEdHiWERZm0E5xpSIy9qmwSwHtE' \
    -F 'file=@exampleFile.data;type=text/plain'
```

Response:
```
{
   "message":"Success! File exampleFile.data saved."
}
```

#### Upload file - without auth

Request:
```
$ curl 'http://localhost:8080/uploadFile' -i -X POST \
    -H 'Content-Type: multipart/form-data;charset=UTF-8' \
    -F 'file=@exampleFile.data;type=text/plain'
```

Response:
```
{
   "error":"unauthorized",
   "error_description":"Full authentication is required to access this resource"
}
```

#### Download file - with auth

Request:
```
$ curl 'http://localhost:8080/downloadFile/exampleFile.data' -i -X GET \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyNDg2ODQsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMGptZ1BjZmhrSDZ0eFBhbFF3U2lKTFRqS2FzIiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.zJ7FYhZ4Dz-fHIC57rbkokTn79ExxzMb745VXis9F-U'
```

Response:
```
File
```

#### Download file - with auth - file not found

Request:
```
$ curl 'http://localhost:8080/downloadFile/exampleFileNotFound.data' -i -X GET \
    -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDEyOTkyOTQsInVzZXJfbmFtZSI6IkpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiVlVOb3Y3Rk1zeTJkNHFseUxHTXF5QUlzS0o4IiwiY2xpZW50X2lkIjoiZXhhbXBsZUNsaWVudElEIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.Wr4HQzvIV1vK2TWpKOC4thDksugxu2rOT-nupn2u13E'
```

Response:
```
{
   "message":"File doesn't exist."
}
```

#### Download file - without auth

Request:
```
$ curl 'http://localhost:8080/downloadFile/exampleFile.data' -i -X GET
```

Response:
```
{
   "error":"unauthorized",
   "error_description":"Full authentication is required to access this resource"
}
```