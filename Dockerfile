FROM bellsoft/liberica-openjdk-alpine:latest
#COPY src/ /src
#RUN javac src/main/java/com/safetynet/SafetyNetAlertsApp.java
#WORKDIR /src
#CMD java main.java.com.SafetyNetAlertsApp
ADD target/safetynetalerts-0.0.1-SNAPSHOT.jar safetynetalerts-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","safetynetalerts-0.0.1-SNAPSHOT.jar"]