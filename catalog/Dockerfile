FROM        openjdk:15

LABEL       author="Gokul Sarath"

WORKDIR     /var/www

COPY        ./target/catalog-app.jar ./

EXPOSE      8082

ENTRYPOINT  ["java", "-jar", "catalog-app.jar"]