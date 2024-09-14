# README

## Deploy

1. `sbt dist`
2. `unzip target/universal/hottocoffee-1.0-SNAPSHOT.zip`
3. `docker build --platform=linux/amd64 -t t45k/hottocoffee_backend:x.y.z .`
4. `docker push t45k/hottocoffee_backend`
5. deploy by Cloud Run
