FROM java:8u111
RUN echo "Asia/Shanghai" > /etc/timezone
RUN dpkg-reconfigure -f noninteractive tzdata
VOLUME /conf
ADD version.txt /version.txt
ADD target/enerbos-eam-openservice-*.jar /app.jar
RUN cd /usr/lib/jvm/java-8-openjdk-amd64/jre/lib && mkdir fonts && cd fonts && mkdir fallback
ADD target/classes/simsun.ttf /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/fonts/fallback/simsun.ttf
RUN bash -c 'touch /app.jar'
EXPOSE 9080
CMD java ${JAVA_OPTS}  -Dfile.encoding=utf-8 -jar /app.jar --spring.config.location=/conf/${CONFIG_FILE_NAME}