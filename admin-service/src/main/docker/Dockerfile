FROM localhost:5000/demo-base-image
VOLUME /tmp
COPY config /config/
RUN ls -la /config/*
ADD admin-service-1.0.0-SNAPSHOT-exec.jar admin-service.jar
RUN bash -c 'touch /admin-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/admin-service.jar", "--spring.config.location=/config/deployment.docker.yml", "--logging.config=/config/logback-spring.xml"]