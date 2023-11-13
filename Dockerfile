FROM eclipse-temurin:17-jdk 
USER root
COPY ./covid-api /covid-api
WORKDIR /covid-api
RUN ./gradlew build -x test


FROM eclipse-temurin:17-jre
COPY --from=0 /covid-api/build/libs/covid-api-0.0.1-SNAPSHOT.jar /covid-api.jar
CMD ["java", "-jar", "/covid-api.jar"]
EXPOSE 8080