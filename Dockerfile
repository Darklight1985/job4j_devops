FROM gradle:8.11.1-jdk21 AS builder
RUN mkdir job4j_devops
WORKDIR /job4j_devops

COPY build.gradle.kts settings.gradle.kts gradle.properties gradle/libs.versions.toml ./
COPY gradle gradle
RUN gradle --no-daemon dependencies

COPY . .
RUN gradle --no-daemon build -x test
RUN jar xf /job4j_devops/build/libs/DevOps-1.0.0.jar

RUN jdeps --ignore-missing-deps -q \
    --recursive \
    --multi-release 21 \
    --print-module-deps \
    --class-path 'BOOT-INF/lib/*' \
    /job4j_devops/build/libs/DevOps-1.0.0.jar > deps.info
RUN jlink \
    --add-modules $(cat deps.info) \
    --strip-debug \
    --compress 2 \
    --no-header-files \
    --no-man-pages \
    --output /slim-jre

FROM debian:bookworm-slim
ENV GRADLE_HOME=/usr/local/gradle-8.11.1
ENV PATH=$GRADLE_HOME/bin:$PATH
ENV JAVA_HOME=/user/java/jdk21
COPY --from=builder /slim-jre $JAVA_HOME
COPY --from=builder /job4j_devops/build/libs/DevOps-1.0.0.jar .
ENTRYPOINT ["java", "-jar", "DevOps-1.0.0.jar"]
