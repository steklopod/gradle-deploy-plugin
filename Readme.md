### [Deploy plugins for gradle](https://login.gradle.org/search?term=colaba.online) [![Build Status](https://travis-ci.org/steklopod/gradle-deploy-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-deploy-plugin)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=bugs)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=steklopod_gradle-docker-plugin&metric=security_rating)](https://sonarcloud.io/dashboard?id=steklopod_gradle-docker-plugin)

### Quick start for multi-module project:

#### build.gradle.kts

```kotlin
description = "ROOT of multi-module project"

plugins {
    val colabaVersion = "0.2.3"
    id("online.colaba.ssh") version colabaVersion
    id("online.colaba.docker") version colabaVersion apply false
}

defaultTasks("compose")

allprojects { tasks { withType<Wrapper> { gradleVersion = "6.0" } } }

subprojects { apply(plugin = "online.colaba.docker" ) }
```

### 🎯 Available gradle's tasks:

[Docker](https://plugins.gradle.org/plugin/online.colaba.docker) [![Build Status](https://travis-ci.org/steklopod/gradle-docker-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-plugin)
* `deploy` - compose-up for project
* `deployDev`  - compose up  docker-service from `docker-compose.dev.yml` file;
* `remove`      - removes docker-service;
* `stop`        - stops docker-container;
* `docker`        - stops docker-container;
* `recompose`   - compose up after removing current docker-service.
* `recomposeDev` - compose up after removing current docker-service from `docker-compose.dev.yml` file.
* `containers`  - print current docker-services;
Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Docker.md)

[Ssh](https://github.com/steklopod/gradle-ssh-plugin) [![Build Status](https://travis-ci.org/steklopod/gradle-ssh-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-ssh-plugin) 
* `publish` - send by ftp
* `ssh` - send by ftp
Docs [here](https://github.com/steklopod/gradle-deploy-plugin/blob/master/readme-Ssh.md)

[Docker-main](https://github.com/steklopod/gradle-docker-main-plugin)[![Build Status](https://travis-ci.org/steklopod/gradle-docker-main-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-main-plugin) 
* `compose` - docker compose up all docker-services with recreate and rebuild
* `recomposeAll` - compose up after removing current docker-service
* `composeNginx`, `composeBack`, `composeFront` - compose up with recreate and rebuild
* `removeAll` - remove all containers
* `prune` - remove unused data
