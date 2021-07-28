Workato Raspberry Pi SenseHAT Extension to support integration with the SenseHAT sensors and LED Matrix.

## Requriements
You will of course need a Rapsberry Pi and the Sense HAT.

1. Install the latest Raspbian on Raspberry Pi.
2. Install the necessary Sense HAT libraries

```sh
sudo apt-get update
sudo apt-get install sense-hat
sudo reboot
```

## Building extension

Steps to build an extension:

1. Install the latest Java 8 SDK
2. Use `./gradlew jar` command to bootstrap Gradle and build the project.
3. The output is in `build/libs`.

## Installing the extension to OPA

1. Add a new directory called `ext` under Workato agent install directory.
2. Copy the extension JAR file to `ext` directory.
  - workato-rpi-sensehat-extension-0.1.jar
3. Update the `config/config.yml` to add the `ext` file to class path.

```yml
server:
   classpath: /opt/opa/workato-agent/ext
```

4. Update the `conf/config.yml` to configure the new extension.

```yml
extensions:
   sensehat:
      controllerClass: com.knyc.opaextension.SenseHatExtension
```
