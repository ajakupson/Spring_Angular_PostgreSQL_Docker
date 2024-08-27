FROM node:alpine

WORKDIR /usr/src/app/frontend

COPY . /usr/src/app/frontend

RUN npm install

RUN npm install -g @angular/cli

CMD ["ng", "serve", "--host", "0.0.0.0"]
