FROM eclipse-temurin:22-jdk AS build

WORKDIR /build
COPY . .
# Download MySQL JDBC driver
ADD https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/8.4.0/mysql-connector-j-8.4.0.jar mysql-connector-j-8.4.0.jar
RUN javac -d out src/*.java && \
    jar --create --file=Warenverkauf.jar --main-class=MainMenu -C out .

FROM eclipse-temurin:22-jre
WORKDIR /app
COPY --from=build /build/Warenverkauf.jar Warenverkauf.jar
COPY --from=build /build/mysql-connector-j-8.4.0.jar mysql-connector-j-8.4.0.jar

ENTRYPOINT ["java", "-cp", "Warenverkauf.jar:mysql-connector-j-8.4.0.jar", "MainMenu"]