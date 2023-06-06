FROM openjdk:8
ENV APP_HOME=/apps
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
WORKDIR $APP_HOME
COPY ./pandama-gateway-1.0.0.jar ./app.jar
EXPOSE 8881
ENTRYPOINT ["java","-Xms515m","-Xmx512m","-jar"]
CMD ["app.jar"]