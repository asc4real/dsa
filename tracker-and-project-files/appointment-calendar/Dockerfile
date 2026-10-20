FROM eclipse-temurin:25-jre
WORKDIR /srv
RUN useradd --uid 10001 --create-home app
USER 10001:10001
COPY --chown=10001:10001 target/appointment-calendar-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=70","-Djava.security.egd=file:/dev/urandom","-jar","/srv/app.jar"]
