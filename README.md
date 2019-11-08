# stripcontrol

This project offers a small webservice with a frontend to control APA102 strips

## Fast dev setup
* run spring boot app (f.i. in eclipse), will run on localhost:8080
* run `npm run dev`, will run on localhost:8090 and proxy requests (in dev mode) to localhost:8080

## RasPi Build workaround
The current frontend-maven-plugin is unable to detect the RasPi architecture correctly (see https://github.com/eirslett/frontend-maven-plugin/issues/704 ). 
Workaround for that, on the Pi:
* create directory & cd into: ~/.m2/repository/com/github/eirslett/node/10.16.0 (or current node version)
* wget the correct binary, f.i.: https://nodejs.org/dist/v10.16.0/node-v10.16.0-linux-armv7l.tar.gz 
  * check `uname -a` output for correct architecture and choose the correct link from https://nodejs.org/dist/v10.16.0/
* copy/rename the downloaded file to the filename which the plugin expects (see the output of the failing build), f.i. node-10.16.0-linux-x86.tar.gz
* run the maven build again (will fail)
* move/copy stripcontrol-frontend/node/tmp/node-v10.16.0-linux-armv7l to stripcontrol-frontend/node/tmp/node-v10.16.0-linux-x86
* 