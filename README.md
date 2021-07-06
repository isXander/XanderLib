# XanderLib
*Not yet implemented.*

XanderLib is a project designed to reduce the total mod folder size while making it easier for 
mod developers to create efficiently.

It also aims at making more code open source and being a hub for all potentially useful code.

## Summary

- [Getting Started](#getting-started)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [Authors](#authors)
- [License](#license)

## Getting Started

These instructions will help you get started to help develop
or test the mod.

### Installing

This is how you can get a working development environment.

Decompile Minecraft

    gradlew setupDecompWorkspace

If you use [Intellij](https://www.jetbrains.com/idea/), then run this command

    gradlew idea genIntellijRuns

If you use [Eclipse](https://www.eclipse.org/) *(not recommended)*, use this

    gradlew eclipse

Now you will be able to open the folder as a project in your chosen IDE.

## Testing

To test the mod, you will need to use a run configuration.

Add an application run configuration with the following details

    Classpath: XanderLib.main
    Main Class: GradleStart

## Deployment

When deploying, you will need to use gradle to build the project.

Add the following line to a new gradle run configuration

    clean build

Once built, add the jar file to the `mods` folder in your minecraft directory commonly found in `%appdata%/.minecraft`

## Contributing

Please make sure to make a useful contribution that will benefit either the user or fellow developers in a noticeable way.

## Authors

- **isXander** - *Founder of the project* -
  [isXander](https://github.com/isXander)
- **Other** - *Github contributors* -
  [Contributor List](https://github.com/isXander/XanderLib/graphs/contributors)

## License

This project is licensed under the [GPL 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)
GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for
details