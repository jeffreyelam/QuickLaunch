# QuickLaunch

Launch files, quickly.

QuickLaunch is a small always-available launcher bar for MYS developers. Press the
global hotkey **Ctrl+J** to focus the input box, type a command, and press **Enter**.

## Commands

Type any of the following into the box and press Enter:

| Command | What it does |
|---|---|
| `help` or `?` | Show the full command list in-app |
| `local` / `dev` / `test` / `staging` (or `stage`) / `production` / `imts` | Switch the active environment. The current environment shows in the box and determines which share paths commands resolve against. Switching to `production` asks for confirmation first. |
| `add` | Add a shortcut — a name plus a file path or a URL (URLs must start with `http`). Optionally mark it "environment specific" so the same relative path resolves against whichever environment is active. |
| `delete` | Delete a shortcut by name |
| `shortcuts` | Open a list of saved shortcuts. Double-click to open; right-click for Open / Delete. |
| `root` | Open the current environment's root folder |
| `core` | Open the current environment's `mys/` folder |
| `exh` | (local only) Open `MYS-ExhDashboard` |
| `git:<showId>` | Set up a `MYS-Shows` branch for a show and copy its staging files. See below. |
| `ip:<address>` | Open a whois lookup for the given IP/host in your browser |
| _a saved shortcut name_ | Open that shortcut |
| _any other text_ | Fuzzy-search folders in the current environment and open matches |
| `exit` | Save shortcuts and quit |

**Tip:** run several commands at once by separating them with commas, e.g. `local,root`.
Use the **Up / Down** arrow keys to recall previously entered commands. Failures show a
brief message in the status line under the input instead of failing silently.

### `git:<showId>` workflow

`git:<showId>` prepares the local `MYS-Shows` repository for a trade show. It requires a
clean working tree, then fetches origin and either checks out the existing `<showId>`
branch (pulling it) or creates it fresh from `main`. It then copies the show's
`boothsales`, `reporting`, and `showfiles` folders from the Staging share into
`MYS-Shows/<showId>/` (skipping `invoices` and `contracts`). Progress is shown in a
small status dialog. Nothing is committed or pushed — **review and commit manually.**

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
mvn clean compile assembly:single
```
This produces `target/QuickLaunch-2.0-SNAPSHOT-jar-with-dependencies.jar`.

### Executing program

* You can simply double click the jar file to execute, or execute the following in terminal:
```
cd path/to/jar/
java -jar ./jar-name.jar
```

### Open on Windows Boot
* Create a .bat file with the following contents (sub your jar path)
```
start javaw -jar C:\Users\<you>\Documents\QuickLaunch-2.0-SNAPSHOT-jar-with-dependencies.jar
```
* Place the file in the following location (again sub your username)
```
C:\Users\<you>\AppData\Roaming\Microsoft\Windows\Start Menu\Programs\Startup
```
* QuickLaunch will now open on boot!