# FoxBase
## Setup
1. Clone this repository.
2. Run the following command from the project's root directory:
```
./gradlew setupDevWorkspace idea genIntellijRuns build
```
3. Open IntelliJ IDEA.
4. Open `Open => Select FoxBase folder`
5. Add VM options `-Dfml.coreMods.load=cn.enaium.foxbase.injection.MixinLoader`