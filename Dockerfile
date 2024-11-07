FROM amazoncorretto:17
VOLUME /tmp
COPY target/data-karyawan-0.0.1-SNAPSHOT.jar data-karyawan-app.jar
ENTRYPOINT ["java", "-jar", "/data-karyawan-app.jar"]