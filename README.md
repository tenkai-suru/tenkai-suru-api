# Tenkai Suru API

API that enables deployments of any kind

## Set Up

Install [Leiningen 2](https://github.com/technomancy/leiningen) if you already haven't. Make sure you have the lein command available in your terminal with `which lein`. Then download the source code with [Git](http://git-scm.com/downloads) by cloning the repository. Now cd into the project's directory. Then start the server with the following command:

```bash
lein run
```

-OR-

```bash
lein uberjar
java -jar target/tenkai-suru-api-0.1.0-standalone.jar
```

When running the project you can set the CONFIG_HOME environment variable to tell the project to read configuration files from a different directory.

You can run the tests with the following command:

```bash
lein spec -a
```
