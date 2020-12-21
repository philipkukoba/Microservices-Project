# https://nodejs.org/en/docs/guides/nodejs-docker-webapp/
# https://medium.com/faun/step-by-step-guide-to-dockerize-a-node-js-express-application-cb6be4159cf1
FROM node:12
WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 8000
ENTRYPOINT ["node","app.js","-p","8000","-i","5","-c","0.85"]