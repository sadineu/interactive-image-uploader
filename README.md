# Configuration to run spring boot back-end project
  1. Download and install Erlang    https://www.erlang.org/downloads
  2. Download and install  RabbitMQ  https://www.rabbitmq.com/download.html
  
  We will now start Rabbit. The above installation should have installed the RabbitMQ command prompt. Open it.
  Go to the RabbitMQ Server Location and use the command as follows- rabbitmq-server start
  
  ### Need to add RabbitMQ configuration in application.properties file accordingly or default configuration
      #RabbitMQ Config
      spring.rabbitmq.host=localhost   // set your host
      spring.rabbitmq.port=5672        // set your port
      spring.rabbitmq.username=guest   // set your username
      spring.rabbitmq.password=guest   // set your password
  
  #### Need to configure your database properties in application.properties file of spring boot back-end project
       #database config
        spring.datasource.url=jdbc:mysql://localhost:3307/app?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false // set your port accordingly
        spring.datasource.username=root  // set your user name
        spring.datasource.password=1234  // set your password
        spring.datasource.platform=mysql
        spring.datasource.initialization-mode=always
        spring.jpa.hibernate.ddl-auto=update
        
  Clone and import this back-end project in your favorite IDE and follow above guideline to configure properly.
  After that just click/hit the run button
  
  ######################### For Front-end ##########
  1. Install nodejs from this link https://nodejs.org/en/
  2. Open cmd and run this command   npm install -g @angular/cli   to install Angular CLI
  3. Go to project directory and run the command npm install
  4. Please intigrate websocket by these two command(Optional)
      npm install @stomp/stompjs
      npm install sockjs-client
      
  4. Go to your Angular project directory run by this command  ng serve // For default port 
  
  
  Back-end endpoint high level explanation :
   we have two endpoints 
      1. Get Api : api/v1/images/get   to get all images 
      2. Post Api : api/v1/images/upload  to upload multiple images
      
      
  
