FROM node:12
WORKDIR /usr/src/app

COPY package*.json ./
COPY . .

RUN npm install

EXPOSE 3000
ENTRYPOINT ["node","boekhoud.js"] 
# opties in docker-compose.yaml meegeven 