# Threat_Intelligence-Report

----------------------------------------


### How to Run

1. Set the output war file name in `pom.xml` file in line `169`.
   <p>e.g. : <code> rest </code>  </p>
2. Build the project with Maven. 
   <p><code> mvnw.cmd install -f pom.xml </code></p>
3. War file is created in
   <p> <code> target/"filename".war </code> </p>
   <p> e.g. :  <code> target/rest.war </code> </p>
   
4. Copy the war file into Tomcat webapps folder
   <p><code> $CATALINE_HOME/webapps/ </code></p>
   
5. Start the Tomcat server. Startup script is in `$CATALINA_HOME/bin/` directory
    - `startup.bat` : for Windows
    - `startup.sh`  : for Linux
      
    
4. Goto `localhost:8080/"filename"/`
   <p>e.g. : <code>localhost:8080/rest/</code> </p>
   

5. End Points : 
    - `api/domain`  : to fetch domains
    - `api/ip`  : to fetch ips

   e.g. : `localhost:8080/rest/api/ip`
<br/>

----------------------------------------------------


### Homepage

- **IP** : to fetch IPs
- **DOMAIN** : to fetch Domains
- **Search box** : to search specific content

<img src="homepage1.png" alt="homepage1.png" />

