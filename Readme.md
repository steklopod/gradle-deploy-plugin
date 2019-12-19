### [Deploy plugins for gradle](https://login.gradle.org/search?term=colaba.online) [![Build Status](https://travis-ci.com/steklopod/gradle-deploy-plugin.svg?branch=master)](https://travis-ci.com/steklopod/gradle-deploy-plugin)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

### Quick start for multi-module project:

* `build.gradle.kts` file example for root of multi-module project:

```kotlin
plugins {
    val colabaVersion = "1.0.0"
    id("online.colaba.ssh") version colabaVersion
    id("online.colaba.docker") version colabaVersion apply false
}

defaultTasks("compose")

allprojects { tasks { withType<Wrapper> { gradleVersion = "6.0" } } }

subprojects { apply(plugin = "online.colaba.docker" ) }
```

### 🎯 Available gradle's tasks:

[Docker](https://plugins.gradle.org/plugin/online.colaba.docker) [![Build Status](https://travis-ci.com/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.com/steklopod/gradle-docker-plugin)
* `deploy` - compose up project from `docker-compose.yml` file (default with recreate & rebuild)
* `deployDev`  - compose up docker container from `docker-compose.dev.yml` file
* `recompose`, `recomposeDev`  - compose up after removing current docker service
* `stop`, `remove`      - stop/remove docker container
* `containers`, `docker`  - print current docker-services
* `execute` - Execute a command line process on local PC (linux/windows)
* `logs` - print logs of current docker container

> Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Docker.md)

[Ssh + Docker Root](https://github.com/steklopod/gradle-ssh-plugin) [![Build Status](https://travis-ci.com/steklopod/gradle-ssh-plugin.svg?branch=master)](https://travis-ci.com/steklopod/gradle-ssh-plugin) 
* `publish` - send by ftp
* `ssh` - send by ftp

* `compose` - docker compose up all docker-services with recreate and rebuild
* `removeAll` - remove `nginx`, `frontend` & `backend` containers 
* `recomposeAll` - compose up after removing `nginx`, `frontend` & `backend` containers
* `composeNginx`, `composeBack`, `composeFront` - compose up with recreate and rebuild
* `prune` - remove unused docker data

> Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Ssh.md)
