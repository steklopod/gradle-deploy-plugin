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
    val colabaVersion = "1.0.2"
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
* `execute` - execute a command line process on local PC (linux/windows)
* `logs` - print logs of current docker container

> Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Docker.md)

[Ssh](https://github.com/steklopod/gradle-ssh-plugin) [![Build Status](https://travis-ci.com/steklopod/gradle-ssh-plugin.svg?branch=master)](https://travis-ci.com/steklopod/gradle-ssh-plugin) 
Send by `ftp` with `ssh`:
1. `publishBack` - copy **backend** distribution `*.jar`-file
2. `publishFront` - copy **frontend** folder
3. `publishGradle` - copy **gradle** needed files
4. `publishDocker` - copy **docker** files
5. `publishNginx` - copy **nginx** folder

All this tasks includes in 1 task:

* `publish` - all enabled  by default (**true**)

>All this tasks excluded in 1, but can be included manually in `ssh` task, where all disabled  by default (**false**)

* `compose` - docker compose up all docker-services with recreate and rebuild
* `composeDev` - docker compose up all docker-services with recreate and rebuild from `docker-compose.dev.yml` file
* `recomposeAll` - docker compose up after removing `nginx`, `frontend` & `backend` containers
* `recomposeAllDev` - docker compose up after removing `nginx`, `frontend` & `backend` containers `docker-compose.dev.yml` file
* `composeNginx`, `composeBack`, `composeFront` - docker compose up with recreate and rebuild

* `prune` - remove unused docker data
* `removeBackAndFront` - remove **backend**, **frontend** containers
* `removeAll` - remove **nginx**, **frontend** & **backend** containers 

> Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Ssh.md)
