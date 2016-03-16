# supercolliderStandaloneRPI2
Standalone for Raspberry Pi 2 Raspbian Jessie including the full IDE.

This is the audio synthesis program [SuperCollider](http://github.com/supercollider/supercollider) (master branch commit 617f980) compiled for armv7l.

It was built using [this guide](http://supercollider.github.io/development/building-raspberrypi.html) on a Raspberry Pi 2 model B under 2016-02-26-raspbian-jessie.img.

The standalone structure is loosely based on [Miguel Negrão's template](https://github.com/miguel-negrao/scStandalone). It is independent (except for one config file: sc_ide_conf.yaml) and can coexist with the Raspbian bundled 3.6.6 version of SuperCollider (used by Sonic Pi - i.e. no need to uninstall Sonic Pi and they can even run simultaneously).

installation
--

open terminal on the RPi and type...

* `sudo apt-get install libqt5webkit5 libqt5sensors5 libqt5positioning5 libcwiid-dev libfftw3-dev`
* `git clone git://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

NOTE: the last command will overwrite or create the global sc_ide preference file from a template. Unfortunately SuperCollider IDE can not use a separate local configuration file, but hopefully this will change in the future. Also note that if you cloned this repository somewhere else than in your pi home directory, edit the yaml file with `nano ~/.config/SuperCollider/sc_ide_conf.yaml` and make the paths in there point to your supercolliderStandalone directory.

scide
--

To run the full IDE first open a terminal window and type...

* `qjackctl`

Select the correct soundcard (under setup/interfaces) and then start jackd.

Then double click the SuperColliderIDE desktop icon.
![alt text](https://raw.githubusercontent.com/supercollider/supercollider/master/icons/sc_ide_48.png "SuperColliderIDE")

SuperCollider IDE should now run like normal - with scope, meter, plot, gui, animation, quarks etc.

advanced
--

if you ever need to start scide from the command line you should do...

* `cd supercolliderStandaloneRPI2`
* `export PATH=.:$PATH`
* `scide`

NOTE: the cd and export commands are necessary before starting scide as we need to use our standalone binaries and not the global ones in /usr/bin.

headless
--

To run sclang+scsynth only from ssh...

* `export DISPLAY=:0.0`
* `jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, 1 usb soundcard
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l sclang.yaml`

NOTE: one can also specify a .scd file to load when starting sclang like this: `./sclang -a -l sclang.yaml mycode.scd`
