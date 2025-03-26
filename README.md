## Meetime - HubSpot Integration

#### Prerequisites
- [JDK-21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/download.cgi)
- [Ngrok](https://ngrok.com/)

## Running the application

##### To run the application:

- Set environment variables for HubSpot integration
  - CLIENT_ID
  - CLIENT_SECRET

- And execute
  ```bash
  ./mvnw spring-boot:run
  ```

- Or run, informing the values:
  ```
  # Replace <value> with the respective values
  ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-DCLIENT_ID=<value> -DCLIENT_SECRET=<value>"
  ```


The API will be made available on: http://localhost:8080

####
##### Ngrok

Webhooks will need to work locally.

- Install ngrok
    - [Linux](https://dashboard.ngrok.com/get-started/setup/linux)
    - [Windows](https://dashboard.ngrok.com/get-started/setup/windows)
####

- Configure ngrok authtoken
  ```
  ngrok config add-authtoken 2uh9ngQvBq6CkMDE80ye7Ahrj0o_5aMLySeZ2vDfa3wkyUVes
  ```

####
- Release the application path externally
  ```
  ngrok http http://localhost:8080
  ```
  
Note: Run commands via command line

###
##### WebHooks

After configuring ngrok, the external address will be made available to redirect to http://localhost:8080, and thus configure the webhook in HubSpot
- Address:
  - https://2b73-2804-3c60-40-5786-e3b7-c09c-ba07-654c.ngrok-free.app

  Note: This is just an example that can be made available by ngrok

####
- Path
  - /webhooks/contacts


## Operating Instructions

#### After uploading the application locally.

- Access the endpoint to get the hubspot authentication url
  - http://localhost:8080/oauth/authorize
  
####

- Access the endpoint provided in the previous step and perform authentication. Once this is done, you will be directed to the callback endpoint that will return the following information:
  - access_token
  - refresh_token
  - token_type
  - expires_in

####

- With the information returned, it will be possible to execute the endpoints created for the integration, informing the "access_token" in the request headers.

### Contact Endpoint

- Create ( Create a contact )
  - http://localhost:8080/contacts
  - Method: Post
  - Header: X-Authorization-HubSpot: <access_token>
  - Body:
    ```
    {
      "firstname": "",
      "lastname": "",
      "email": ""
    }
    ```

Other endpoints created

####
- List ( List contacts )
  - http://localhost:8080/contacts
  - Method: Get
  - Header: X-Authorization-HubSpot: <access_token>

####
- Get ( Search for a contact )
  - http://localhost:8080/contacts/<id_contato>
  - Method: Get
  - Header: X-Authorization-HubSpot: <access_token>


### Webhook Endpoint

After receiving the HubSpot event via WebHook, the information received will be printed in the application log. 
```
Webhook { Event Id: '100', Type: 'contact.creation', recebido para o Object Id: '123' }
```