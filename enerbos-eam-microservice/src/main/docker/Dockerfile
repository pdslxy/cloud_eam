FROM java:8u111
RUN echo "Asia/Shanghai" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
VOLUME /conf
ADD version.txt /version.txt
ADD target/enerbos-eam-microservice-*.jar /app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 9081
CMD java ${JAVA_OPTS}  -Dfile.encoding=utf-8 -jar /app.jar --spring.config.location=/conf/${CONFIG_FILE_NAME}