FROM        openjdk:15

LABEL       author="Gokul Sarath"

WORKDIR     /var/www

COPY        ./target/order-app.jar ./

EXPOSE      8083

ENTRYPOINT  ["java", "-jar", "order-app.jar"]