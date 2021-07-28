Workato Raspberry Pi SenseHAT Extension to support integration with the SenseHAT sensors and LED Matrix.

## Credits

This project uses the [Java Wrapper for Raspberry Pi Sense Hat](https://github.com/cinci/rpi-sense-hat-java) extensively.

## Requriements
You will of course need a Rapsberry Pi and the Sense HAT. I've tested this with a Raspbery Pi 3.

1. Install the latest Raspbian on the Raspberry Pi.
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

## Custom SDK for the extension

The corresponding custom SDK can be found here in this repo as well.

Link: [raspberry-pi-sensehat-sdk.rb](custom-sdk/raspberry-pi-sensehat-sdk.rb)

Create a new Custom SDK in your Workato workspace and use it with the OPA extension.