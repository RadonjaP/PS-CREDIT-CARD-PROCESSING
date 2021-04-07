# PS-CREDIT-CARD-PROCESSING
Task for interview with Publicis Sapient


API Access:

  - Username: ccpUser
  - Password: ccpPass
  
  Basic Auth is required with provided username and password. Otherwise 401 code will be returned to warn you about lack of authority.
  
  To obtain my session Id, I used tool called "Postman" which has security option "Basic Auth" with username and password.
  Same can be achieved with curl command with following syntax:
   
    curl -i --user ccpUser:ccpPass http://localhost:8080/credit-cards/getAll
    
  After you get session id you can easily use it to call POST method too.
  
  
  Application Starting:
  
    When starting application it is required to add environment variables. These variables represent username and password that will be required to access application. 
    Properties were externalized because if, for example, we need different login details for different environments. 
    
    -DCCP_AUTH_USERNAME=ccpUser -DCCP_AUTH_PASSWORD=ccpPass
