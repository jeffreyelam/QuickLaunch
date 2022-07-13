# QuickLaunch

Launch files, quickly.

## Releases
A ready-to-use executable is provided  [here](https://github.com/jeffreyelam/QuickLaunch/releases). These releases are provided to ensure every developer does not need to download and compile the source code.

## Getting Started
### Installing

* Clone the source code
```
* https://github.com/jeffreyelam/QuickLaunch
```
* Open the program in IntelliJ
* Open the maven tab on the right side of IntelliJ
* Click the maven icon "execute maven goal", and run the maven command
```
maven clean compile assembly:single
```

### Executing program

* You can simply double click the jar file to execute, or execute the following in terminal:
```
cd path/to/jar/
java -jar ./jar-name.jar
```

### Open on Windows Boot
* Create a .bat file with the following contents (sub your jar path)
```
start javaw -jar C:\Users\ctooley\Documents\QuickLaunch-1.0-SNAPSHOT-jar-with-dependencies.jar
```
* Place the file in the following location (again sub your username)
```
C:\Users\ctooley\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
```
* QuickLaunch will now open on boot!