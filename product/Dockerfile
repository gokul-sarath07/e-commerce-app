FROM        openjdk:15

LABEL       author="Gokul Sarath"

WORKDIR     /var/www

COPY        ./target/product-app.jar ./

EXPOSE      8081

ENTRYPOINT  ["java", "-jar", "product-app.jar"]