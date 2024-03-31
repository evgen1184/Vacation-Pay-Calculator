FROM alpine:3.16

RUN apk add openjdk11
ADD /build/libs/vacation-pay-calculator-0.0.1-SNAPSHOT.jar vacation.jar

ENTRYPOINT ["java", "-jar", "vacation.jar"]
