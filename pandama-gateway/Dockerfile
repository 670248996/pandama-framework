FROM openjdk:8
ENV APP_HOME=/apps
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
WORKDIR $APP_HOME
COPY ./pandama-gateway-1.0.0.jar ./gateway.jar
EXPOSE 7771
ENTRYPOINT ["java","-Xms515m","-Xmx512m","-jar"]
CMD ["gateway.jar"]