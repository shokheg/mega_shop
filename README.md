# ВНИМАНИЕ!!! ЭТО ПРИМЕР ДОКУМЕНТАЦИИ
#ВАМ ТОЖЕ ОЧЕНЬ ПРИГОДИТСЯ

## Сущности

[Схема базы данных](https://dbdiagram.io/embed/614c9d0e825b5b0146107aac)

```
* - поля ссылающиеся на другие Entity;
** - поля ссылающиеся на Enum.
```

### Address:

#### Поля:

- **id** - уникальный идентификационный номер адреса;
- **cityIndex** - поле хранящее почтовый индекс;
- **country** - поле хранящее Страну*;
- **city** - поле хранящее Город*;
- **street** - поле хранящее название улицы;
- **house** - поле хранящее дом (String);
- **user** - поле связывающие адрес с конкретным Пользователем*.

```
Сущность представляет собой набор данных предназначенных для определения 
адреса доставки заказа/ расположения магазина / расположения пользователя.
```

### CartItem:

#### Поля:

- **id** - уникальный идентификационный номер карточки товара;
- **item** - поле хранящее выбранный Товар*;
- **shop** - поле хранящее Магазин* в котором был выбран товар;
- **user** - поле хранящее Пользователя* который выбрал товар;
- **quantity** - поле хранящее количество товаров.

#### Методы:

- **getSubTotal()**- метод который подсчитывает стоимость товара на основании предоставленной скидки и количества
  выбранного товара.

## Docker

Упаковываем приложение в архив jar для дальнейшего запуска его в контейнере докера. *Приложение должно запускаться и работать*.

Устанавливаем Docker Desktop с официального [сайта](https://www.docker.com/).
Создаем в корневой папке проекта файл - **Dockerfile**:
<details>
<summary>Dockerfile</summary>

```
FROM amazoncorretto:11.0.15-alpine3.15

WORKDIR /javaApp

EXPOSE 8888

COPY ./target .

ENTRYPOINT ["java", "-jar", "project-0.0.1-SNAPSHOT.jar"]
```

- **FROM** - указывает образ ОС (amazoncorretto:11.0.15-alpine3.15 - это один из многих образов с установленной JDK) - на чём будет работать наше приложение в контейнере. Docker сам загрузит на ваш компьютер образ из Docker Hub.
- **WORKDIR** - это рабочая директория в нашем образе, куда будет скопированы файлы нашего приложения.
- **EXPOSE** - указываем на каком порту работает приложение.
- **COPY** - команда, которая копирует файлы из папки **target** в рабочую директорию (javaApp).
- **ENTRYPOINT** - здесь происходит выполнение команды запуска нашего приложения.
</details>

Создаем образ докера, командой:
```
docker build . -t {имя образа}:{версия(необязательно)}
```
Посмотреть образы, которые есть на вашем компьютере и информацию о них, можно командой:
```
docker images
```
Проверяем созданный образ командой:
```
docker run {имя образа:версия - либо ID образа}
```

Загружаем с Docker Hub образ базы данных и сервер nginx. Можно создать соответствующий Dockerfile, можно указать всё в одном файле Dockerfile, можно загрузить командой:
```
docker pull {имя образа}
```

Создаем в корневой папке файл - **docker-compose.yml**:
<details>
  <summary>docker-compose.yml</summary>

```
version: '3'

services:
  nginx-server:
    restart: always
    container_name: 'nginx-server'
    image: 'sithai/nginx:v1'
    depends_on:
      - front_app
    ports:
      - '80:80'

  front_app:
    container_name: 'project-avito4_1_front'
    image: 'sithai/avito4_1_front'
#    ports:
#      - '3000:3000'
  mysql:
    container_name: 'mysql-image'
    image: 'sithai/mysql:8.0.25'
    environment:
      - 'MYSQL_ROOT_PASSWORD=root'
      - 'MYSQL_DATABASE=platform'

  app:
    container_name: 'project-avito4_1_back'
    image: 'sithai/avito4_1_app:latest'
    depends_on:
      - mysql
    ports:
      - '8888:8888'
    environment:
      - SPRING_DATASOURCE_URL=jdbc:mysql://mysql-image:3306/platform?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
      - SPRING_DATASOURCE_USERNAME=root
      - SPRING_DATASOURCE_PASSWORD=root
      - SPRING_DATASOURCE_PLATFORM=org.hibernate.dialect.MySQL8Dialect
```
Файлы конфигурации nginx:

- **nginx.conf**:
```
user  nginx;
worker_processes  auto;

error_log  /var/log/nginx/error.log notice;
pid        /var/run/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       /etc/nginx/mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;


    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;
    upstream front_app {
        server front_app:3000;
    }

    include /etc/nginx/conf.d/*.conf;

}
```
- **default.conf**:
```
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;

    #access_log  /var/log/nginx/host.access.log  main;

    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_pass http://front_app;
    }

    error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    # proxy the PHP scripts to Apache listening on 127.0.0.1:80
    #
    #location ~ \.php$ {
    #    proxy_pass   http://127.0.0.1;
    #}

    # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
    #
    #location ~ \.php$ {
    #    root           html;
    #    fastcgi_pass   127.0.0.1:9000;
    #    fastcgi_index  index.php;
    #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
    #    include        fastcgi_params;
    #}

    # deny access to .htaccess files, if Apache's document root
    # concurs with nginx's one
    #
    #location ~ /\.ht {
    #    deny  all;
    #}
}
```
</details>
<details>
<summary>Dockerfile - фронт приложения.</summary>

```
FROM node:alpine3.16

WORKDIR /webapp

COPY package.json /webapp/package.json

RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

RUN npm install

COPY . /webapp/

RUN npm run build

RUN npm install -g serve

EXPOSE 3000

ENTRYPOINT ["serve", "-s","-n", "build"]

FROM nginx:1.23.0-alpine

RUN apk add nano

COPY default.conf /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/nginx.conf
```
</details>

Запускаем и проверяем работоспособность образов командой **docker-compose up**. 
Остановить работу приложения можно сочетанием клавиш *Ctrl+C* и далее командой 
**docker-compose down**, либо через приложение Docker.

Регистрируемся на Docker Hub и загружаем туда получившиеся образы. Командами:
```
docker tag {имя репозитория}/{имя образа}:{версия}
docker login
docker push {имя репозитория}/{имя образа}:{версия}
```

Заходим на сервер. Если надо, то устанавливаем docker-compose на сервер.
```
curl -L "https://github.com/docker/compose/releases/download/v2.6.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
v2.6.1 - последняя версия docker-compose на 04.06.2022
```
Загружаем на сервер образы из своего репозитория.
```
docker pull {имя репозитория}/{имя образа}:{версия}
```
## Запускаем *docker-compose up*.
