# supercolliderStandaloneRPI2
Standalone for Raspberry Pi 2 Raspbian Jessie including the full IDE.

This is the audio synthesis program [SuperCollider](http://github.com/supercollider/supercollider) (master branch commit 617f980, 21feb2016) compiled for armv7l.

It was built using [this guide](http://supercollider.github.io/development/building-raspberrypi.html) on a **Raspberry Pi 2** model B under 2016-02-26-raspbian-jessie.img. It also works on the **Raspberry Pi 3**. For **Raspberry Pi 1** and **Raspberry Pi Zero** use [this repository](https://github.com/redFrik/supercolliderStandaloneRPI1).

The standalone structure is loosely based on [Miguel Negrão's template](https://github.com/miguel-negrao/scStandalone). This standalone is self-contained and all files are in one directory (except for the sc_ide_conf.yaml file - see below). It can coexist with the Raspbian bundled 3.6.6 version of SuperCollider used by Sonic Pi (i.e. no need to uninstall Sonic Pi and the two programs can even run simultaneously).

installation
--

open the terminal on the RPi and type...

* `sudo apt-get install libqt5webkit5 libqt5sensors5 libqt5positioning5 libcwiid-dev libfftw3-dev`
* `git clone git://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

NOTE: the last command will create a global sc_ide preference file from a template. At the moment SuperCollider IDE can not use a local configuration file, but hopefully this will change in the future. Also note that if you cloned this repository somewhere else than in your pi home directory you should edit the yaml file with `nano ~/.config/SuperCollider/sc_ide_conf.yaml` to make the paths in there point to your standalone directory.

scide
--

To run the full IDE first open a terminal window and type...

* `qjackctl`

Select the correct soundcard (under setup/interfaces) and then start jackd.

Then double click the SuperColliderIDE desktop icon...

![alt text](https://raw.githubusercontent.com/supercollider/supercollider/master/icons/sc_ide_48.png "SuperColliderIDE")

SuperCollider IDE should start and run like normal - with scope, meter, plot, gui, animation, help, quarks etc.

advanced
--

if the desktop shortcut does not work or you want to start scide from the command line you do...

* `cd supercolliderStandaloneRPI2`
* `export PATH=.:$PATH`
* `scide`

NOTE: the cd and export commands are necessary before starting scide as we need to use our standalone binaries and not the global Raspbian ones in /usr/bin/

NOTE: if the scide desktop shortcut is missing the typical icon (perhaps because you uninstalled the bundled sc), you can install it manually like this...

`sudo curl -o /usr/share/pixmaps/sc_ide.svg https://raw.githubusercontent.com/supercollider/supercollider/master/icons/sc_ide.svg`

headless
--

To run sclang+scsynth only from ssh...

* `export DISPLAY=:0.0`
* `jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, and 1 the usb soundcard
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l sclang.yaml`

NOTE: one can also specify a .scd file to load when starting sclang like this: `./sclang -a -l sclang.yaml mycode.scd`

jessie-lite
--

The standalone also works under jessie-lite if the following additional steps are taken...

installation:

* `sudo apt-get install git dbus-x11 xvfb jackd2` #enable realtime when asked

startup:

* `export DISPLAY=:0.0`
* ``export `dbus-launch | grep ADDRESS` ``
* ``export `dbus-launch | grep PID` ``
* `jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, and 1 the usb soundcard
* `cd supercolliderStandaloneRPI2`
* `xvfb-run --auto-servernum ./sclang -a -l sclang.yaml`

autostart
--

todo
