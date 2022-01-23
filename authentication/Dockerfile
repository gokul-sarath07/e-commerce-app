FROM        openjdk:15

LABEL       author="Gokul Sarath"

WORKDIR     /var/www

COPY        ./target/authentication-app.jar ./

EXPOSE      8080

ENTRYPOINT  ["java", "-jar", "authentication-app.jar"]