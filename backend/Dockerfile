FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --chmod=755 ./hottocoffee-1.0-SNAPSHOT/ /app/hottocoffee

EXPOSE 8080

ENTRYPOINT ["/app/hottocoffee/bin/hottocoffee"]
