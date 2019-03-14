# eventparser
parse the events present in log file and store alerts


Run application as spring-boot application

Log file needs to be added at
  src/main/resources/sample.log

To know all the completed events
  http://localhost:8080/v1/event

To know all the events which took more than 4ms
  http://localhost:8080/v1/event/alert
  
  

Note: Due to time constraints below decisions were taken
  1. Maven build tool used instead of Gradel
  2. Alerts were written in the table instead of a seperate files
  3. Sample tests were written and not all files were included
  4. Spring-boot used as it provides starting code quickly.
