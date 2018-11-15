# DeskClock

A simple desktop clock (and weather) application.

Icons made by [Pixel perfect](https://www.flaticon.com/authors/pixel-perfect) from [www.flaticon.com](https://www.flaticon.com/
is licensed by [CC 3.0 BY](http://creativecommons.org/licenses/by/3.0/).

Weather information comes from https://openweathermap.org/api. 

## Usage

You will need an API key for the OpenWeatherMap API.

    java -jar deskclock.jar <zip-code> <api-key>

![Clock screenshot](screenshot.png "Screenshot")

### On Windows

On Windows, you can run the clock without a console by creating a `.bat` file similar to:

```bat
@echo off
start javaw -jar deskclock-0.0.1-all.jar <zip> <key>
```

Where `<zip>` and `<key>` are replaced by their appropriate values.
