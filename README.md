# supercolliderStandaloneRPI2
standalone for raspberry pi 2 raspbian jessie including the full scide.

this is supercollider (master branch commit 617f980) <http://github.com/supercollider/supercollider> compiled for armv7l.

it was built using this guide <http://supercollider.github.io/development/building-raspberrypi.html> under 2016-02-26-raspbian-jessie.img.

and the standalone structure is based on Miguel Negrão's <https://github.com/miguel-negrao/scStandalone> template.

installation
--

open terminal on the rpi and type...

* `sudo apt-get install libqt5webkit5 libqt5sensors5 libqt5positioning5 libcwiid-dev libfftw3-dev`
* `git clone git://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir -p ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

note: the last command will overwrite/create the global scide preference file from our template. unfortunately supercollider ide can not use a separate local config file, but hopefully this will change in the future.
note: if you cloned this repro somewhere else than in your home directory, edit the paths to match in `nano ~/.config/SuperCollider/sc_ide_conf.yaml`

scide
--

to run the full ide first open a terminal window and type...

* `qjackctl`

and start jack with the correct soundcard (under setup/interfaces)

then open another terminal window and type...

* `cd supercolliderStandaloneRPI2`
* `export PATH="${HOME}/supercolliderStandaloneRPI2:$PATH"`
* `./start.sh`

headless
--

to run it from ssh...

* `cd supercolliderStandaloneRPI2`
* `export DISPLAY=:0.0`
* `jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, 1 usb soundcard
* `./sclang -a -l sclang.yaml` #one can also specify a .scd file to load like this: `./sclang -a -l sclang.yaml mycode.scd`

autostart
--

todo