FROM nginx:1.24.0
COPY ./dist /usr/share/nginx/html
EXPOSE 80