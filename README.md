# evolutions

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.



Processes to implement: 
- procedural generation of terrain (first 2d overhead)





- variation of terrain and creature features
    




- genetic algorithm to evolve creatures
    we need to implement a form of stability and counter balancing for the creatures such that rapid evolution does not cause the extinction of the species
    or, recreate new species in the place of extinct species ?
    
    treat creature features as genes. possible genes include: 

        speed : distance multiplier at cost of strength
        strength : higher chance of surviving creature encounter at cost of decreasing speed
        sex appeal : higher chance of being selected as mate at cost of sense / foraging ability 
        breeding frequency : more children at cost of worse children
        cardio : more energy at cost of sex appeal? (bros too locked in for breeding lmao)
        foraging ability / senses : (higher sense radius / likelihood of finding prey at cost of cardio)
        likeability : less intraspecies competition at cost of sex appeal 
        aggressiveness : more sex appeal at cost of intraspecies cooperation
    
    create daily routines for creatures, which include mechanisms for:

        death : starving, dying of thirst, being preyed upon, competition with fellow species members
        survival finding food, water, finding prey or escaping predators
        passing on genes : breeding

    gene passing needs to take in two parent genes, choose one, and add in chance of random mutation 

- optimisation of genetic algorithm
    have everything be calculated without visuals, perhaps in parallel
    do evolution runs beforehand, save them so in presentation we can show a finished one and pretends its live LMAO



- conversion to 3d 







- optimisation of viewing 3d terrain
    