FROM openjdk:11

ENV MUSL_LOCALE_DEPS cmake make musl-dev gcc gettext-dev libintl
ENV MUSL_LOCPATH /usr/share/i18n/locales/musl
ENV TERM=xterm TZ=America/Sao_Paulo

RUN apk update \
    apk upgrade \
    apk add ca-certificates && update-ca-certificates \
    apk add --update tzdata

RUN apk add --no-cache \
    $MUSL_LOCALE_DEPS \
    && wget https://gitlab.com/rilian-la-te/musl-locales/-/archive/master/musl-locales-master.zip \
    && unzip musl-locales-master.zip \
      && cd musl-locales-master \
      && cmake -DLOCALE_PROFILE=OFF -D CMAKE_INSTALL_PREFIX:PATH=/usr . && make && make install \
      && cd .. && rm -r musl-locales-master

ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:br
ENV LC_ALL pt_BR.UTF-8


ARG JAR_FILE=target/productengineering*.jar
COPY ${JAR_FILE} productengineering.jar
ENTRYPOINT ["java","-jar","/productengineering.jar"]


##############


# Etapa de construção
#FROM maven:3.9-eclipse-temurin-17 AS buildstage
#COPY pom.xml /tmp/pom.xml
#COPY domain/ /tmp/domain/
#COPY persistence/ /tmp/persistence/
#COPY webAPI/ /tmp/webAPI
#WORKDIR /tmp
#RUN mvn clean package -DskipTests

# Etapa de runtime
#FROM eclipse-temurin:17 AS runtime

#ARG BUILD_DATE
#ARG BUILD_NUMBER
#ARG COMMIT

#LABEL build-date=$BUILD_DATE \
#      build-number=$BUILD_NUMBER \
#      description="Docker image for product" \
#      commit=$COMMIT \
#      vendor="Todeschini"

#WORKDIR /app
#COPY --from=buildstage /tmp/webAPI/target/webAPI*.jar app.jar

#EXPOSE 8080

#ENV JAVA_OPTS=""

#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]