## 📦 Getting Started

To run the project locally:

### 1️⃣ Clone the repository

```bash
git clone https://github.com/oleksiinutlis/ecosystems_generation
cd ecosystems_generation
```

### 2️⃣ Run the application

#### 💻 On Windows:
```bash
gradlew.bat run
```

#### 🍎 On Mac/Linux:
```bash
./gradlew run
```

# evolutions




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
