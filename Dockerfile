FROM maven:3.8.3-openjdk-17

WORKDIR /fund-loader
COPY . .
RUN mvn clean install

ADD schema.sql /etc/mysql/schema.sql

RUN sed -i 's/MYSQL_DATABASE/'$MYSQL_DATABASE'/g' /etc/mysql/data.sql
RUN cp /etc/mysql/data.sql /docker-entrypoint-initdb.d

CMD mvn spring-boot:run