FROM node:18-alpine as build-step
MAINTAINER Andrey Di CLaudio
WORKDIR /app
COPY package*.json /app/

COPY . .
RUN npm install -g ionic
RUN npm install
COPY ./ /app/
RUN ionic build --prod

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/* && rm -rf /etc/nginx/nginx.conf
COPY ./nginx.conf /etc/nginx/nginx.conf
COPY --from=build-step /app/www /usr/share/nginx/html
