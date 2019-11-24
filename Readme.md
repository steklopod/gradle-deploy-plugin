## Gradle `deploy` plugins [![Build Status](https://travis-ci.org/steklopod/gradle-deploy-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-deploy-plugin)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

### [3 Plugins for easy deploy](https://login.gradle.org/search?term=colaba.online)
[`Docker plugin`](https://github.com/steklopod/gradle-docker-plugin) [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
, [`Docker Main plugin`](https://github.com/steklopod/gradle-docker-main-plugin) [![Build Status](https://travis-ci.org/steklopod/gradle-docker-main-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-main-plugin)
, [`Ssh plugin`](https://github.com/steklopod/gradle-ssh-plugin) [![Build Status](https://travis-ci.org/steklopod/gradle-ssh-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-ssh-plugin)

#### build.gradle.kts

```kotlin
description = "ROOT"

plugins {
    val colabaVersion = "0.1.9"
    id("online.colaba.dockerMain") version colabaVersion
    id("online.colaba.ssh") version colabaVersion
    id("online.colaba.docker") version colabaVersion apply false

}

defaultTasks("compose")

allprojects { tasks { withType<Wrapper> { gradleVersion = "6.0" } } }

subprojects { apply(plugin = "online.colaba.docker" ) }
```

### 🎯 Available gradle's tasks:

> Docker
* `compose` - compose-up for project
* `containers`  - print current docker-services;
* `stop`        - stops docker-container;
* `remove`      - removes docker-service;
* `recompose`   - compose up after removing current docker-service.
* `composeDev`  - compose up  docker-service from `docker-compose.dev.yml` file;
* `recomposeDev` - compose up after removing current docker-service from `docker-compose.dev.yml` file.

> Docker-main
* `compose` - docker compose up all docker-services with recreate and rebuild
* `recomposeAll` - compose up after removing current docker-service
* `composeNginx`, `composeBack`, `composeFront` - compose up with recreate and rebuild
* `removeAll` - remove all containers
* `prune` - remove unused data

> Ssh
* `publish` - send by ftp
* `ssh` - send by ftp

_Name of service for all tasks equals to `${project.name}`. You can customize options of each task._
