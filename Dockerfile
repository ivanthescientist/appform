FROM openjdk:8
COPY target/appform.jar appform.jar
RUN chmod 775 appform.jar
CMD java -jar appform.jar