# Use a imagem oficial do OpenJDK para executar nossa aplicação Java
FROM openjdk:17-jdk-alpine

# Variáveis de ambiente para a aplicação Java
ENV APP_FILE spring-zen-0.0.1-SNAPSHOT.jar
ENV APP_HOME /usr/app

# Cria o diretório da aplicação no container
RUN mkdir $APP_HOME
WORKDIR $APP_HOME

# Copia o jar da nossa aplicação para o container
COPY target/$APP_FILE $APP_HOME/

# Expõe a porta 8081 para acessar a aplicação
EXPOSE 8081

# Comando para executar a aplicação
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $APP_FILE"]