# Izrada weba

## Credits

During development of this website these resources were used for inspiration and ideas:

- [A collection of handy generative art utilities - library](https://github.com/georgedoescode/generative-utils)
- [Fast, small color manipulation and conversion for JavaScript - library](https://github.com/bgrins/TinyColor)
- [Generative macOS Big Sur waves - codepen](https://codepen.io/georgedoescode/pen/bGBzGKZ)
- [Glassmorphism Creative Cloud App Redesign - codepen](https://codepen.io/TurkAysenur/pen/ZEpxeYm)
- [HSL Calculator - utility](https://www.w3schools.com/colors/colors_hsl.asp)
- [Hsl to hex - function](https://stackoverflow.com/questions/36721830/convert-hsl-to-rgb-and-hex)
- [SVG animate tag - tutorial](https://codeburst.io/svg-morphing-the-easy-way-and-the-hard-way-c117a620b65f)

The design of this website was inspired by a codepen "Glassmorphism Creative Cloud App Redesign". The design style is called glassmorphism. I have translated the design line by line to tailwind CSS. There are minor subtle differences in order for me to adapt the codepen to my needs.

The background in the codepen is a highly optimized video. I did not like how it looped and I knew that I would be bored with it in the long run. I found an another codepen "Generative macOS Big Sur waves" which generated random macOS Big Sur waves as an SVG using JS. I copied the code and it looked great except for the massive FOUC (Flash of un-styled content) which was happening because JS needs to load and run in order to generate a background. The solution that I have found is to "translate" the JS code to Scala and have it generated server side on each request. This enabled me to avoid the FOUC and speed up the website because there was no need for JS for that.

Having the background image be different on each request was great, but it was lacking animation. I have read a tutorial on SVG animations and have found a way to animate the waves using SVG animations instead of relying on JavaScript (which was my first idea). By using SVG animations I have reduced the CPU usage, and the animations are smooth.

The entire website is made with Scala and Tailwind CSS, even the JavaScript code is written using Scala and compiled to JavaScript.


## Quickstart

```
npm install

# to compile and run once
sbtn run

# or to compile and run on source change
sbtn ~reStart
```
Other useful commands:

```
# run this on work start (sbt server)
sbtn

sbtn reStatus
```

## Deployment


For docker image:

```
sbt Docker/publishLocal

docker run -p 80:8080 --env-file .env --restart unless-stopped -d  website:0.1.0-SNAPSHOT
```

For zip file:

```
sbt dist
```

Unzip the file and run the binary with `./bin/website`.

## Scala.js

```bash
# development
sbt js/fastLinkJS

# production
sbt js/fullLinkJS
```

## Tailwind CSS

```bash
# watch
npm run watch

# production
npm run prod
```

## AWS SES

In the code there are two versions: v1 and v2.
I would have only created a v2 version, but I have by mistake first created a mailer using v1 version of AWS SDK.
Only later have I realized that there is a v2 of the SDK and how to use it.

## Troubleshooting


### Inspect files in a jar:

```
jar tf .\target\universal\website-0.1.0-SNAPSHOT\lib\eu.izradaweba.website-0.1.0-SNAPSHOT.jar
```


### No DRI found for query: ExitCode

According to @armanbilge this is a scala 3 related bug: 

```
sbt:Website> dist
[info] Wrote C:\Users\mario\code\src\github.com\mabasic\izradaweba\target\scala-3.2.0\website_3-0.1.0-SNAPSHOT.pom
[info] Main Scala API documentation to C:\Users\mario\code\src\github.com\mabasic\izradaweba\target\scala-3.2.0\api...
[info] Skipping unused scalacOptions: -Xsemanticdb, -semanticdb-target
[warn] -- Warning: src\main\scala\eu\izradaweba\Main.scala:121:6 ----------------------
[warn] 121 |  def run(args: List[String]): IO[ExitCode] =
[warn]     |      ^le / doc 0s
[warn]     |      No DRI found for query: ExitCode
[warn] one warning found
[info] Main Scala API documentation successful.
[success] All package validations passed
[info] Your package is ready in C:\Users\mario\code\src\github.com\mabasic\izradaweba\target\universal\website-0.1.0-SNAPSHOT.zip
[success] Total time: 6 s, completed 22. stu 2022. 20:51:10
```

We have tried adding this dependency but it still did not work:

> add "org.typelevel" %% "cats-effect" % "3.4.1" as a dependency


### Cyclic reference involving object Content

```
exception caught when loading trait Builder: Cyclic reference involving object Content
exception caught when loading module class Content$: Cyclic reference involving object Content
```

Related issue: https://github.com/lampepfl/dotty/issues/15288

