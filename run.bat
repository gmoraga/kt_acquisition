set DB_SCHEMA=kitchen_talk
set DB_USER=postgres
set DB_PASSWORD=test1234
set DB_DATABASE=postgres
set DB_POOL_SIZE=10
set DB_QUERY_TIMEOUT=20000
set DB_HOST=localhost
set APP_PORT=9012

set EVENT_EMPLOYEE=EmployeeListenerEvent
set EVENT_PORT_ACQUISITIONS=10012
set API_URL_GET_EMPLOYEE_ID=http://localhost:9002/api/v1/employees/{idEmployee}

mvn clean package

java -Xms256m -Xmx512m -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory -Dlogback.configurationFile=src/main/resources/logback.xml -jar target/kt-acquisition-fat.jar
