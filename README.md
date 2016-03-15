# supercolliderStandaloneRPI2
standalone for raspberry pi 2 & 3 raspbian jessie including the full scide.

this is supercollider version 3.7 <http://github.com/supercollider/supercollider> compiled for arm7.

it was built using this guide <http://supercollider.github.io/development/building-raspberrypi.html> under 2016-02-09-raspbian-jessie.img.

and the standalone structure is based on Miguel Negrão's <https://github.com/miguel-negrao/scStandalone> template.

installation
--

open terminal on the rpi and type:

* `git clone git://github.com/redFrik/supercolliderStandaloneRPI2`
* `sudo apt-get install libqt5webkit5 libqt5sensors5 libqt5positioning5 libcwiid-dev libfftw3-dev`
* create an sc_ide_conf.yaml file TODO: add howto here

then start sc by...

* `cd supercolliderStandaloneRPI2`
* `qjackctl`
* `./run.sh`

headless
--

to run it from ssh...

* `cd supercolliderStandaloneRPI2`
* `export DISPLAY=:0.0`
* `jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, 1 usb soundcard
* `./sclang -a -l sclang.yaml` #also you can give it a scd file to load like this: `./sclang -a -l sclang.yaml mycode.scd`

autostart
--

todo