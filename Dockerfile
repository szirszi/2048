FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

#ENTRYPOINT ["run.sh"] - not working
#ENTRYPOINT ["java","-jar","/app.jar"]
#FROM eclipse-temurin:17-jre-alpine
#VOLUME /tmp
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
#COPY run.sh .

#
#ARG DEPENDENCY=/app/build/dependency
#
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]

#ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "Application"]
#ARG JAR_FILE
#COPY ${JAR_FILE} app.jar
# COPY run.sh .
#COPY build/libs/*.jar app.jar
#ENTRYPOINT ["run.sh"] - not working
#ENTRYPOINT ["java","-jar","/app.jar"]
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]