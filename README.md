# supercolliderStandaloneRPI2
Standalone for Raspberry Pi 2 or 3 with Raspbian Jessie including the full IDE.

This is the audio synthesis program [SuperCollider](http://github.com/supercollider/supercollider) (3.8.0, commit 0947edd, 5nov2016) + [sc3-plugins](https://github.com/supercollider/sc3-plugins) (master, commit f1200cd, 8nov2016) compiled for rpi2 and rpi3.

It was built using [this guide](http://supercollider.github.io/development/building-raspberrypi.html) on a **Raspberry Pi 3** under [2016-09-23-raspbian-jessie](http://raspberrypi.org/downloads/raspbian/) (Raspbian Jessie with Pixel). It also works on the **Raspberry Pi 2**.
For **Raspberry Pi 1** and **Raspberry Pi Zero** use [this repository](https://github.com/redFrik/supercolliderStandaloneRPI1).

The standalone structure is loosely based on [Miguel Negrão's template](https://github.com/miguel-negrao/scStandalone). This standalone is self-contained and all files are in one directory (except for the sc_ide_conf.yaml file - see below). It can coexist with the Raspbian bundled 3.6.6 version of SuperCollider used by Sonic Pi (i.e. no need to uninstall Sonic Pi and the two programs can even run simultaneously).

installation
--

_(this assumes you have done all the usual initialization... burned the disk image, booted, changed password, enabled ssh)_

open the terminal on the RPi and type...

* `sudo apt-get update`
* `sudo apt-get install libqt5webkit5 libqt5sensors5 libqt5positioning5 libqt5concurrent5 libfftw3-bin libcwiid1`
* `git clone https://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir -p ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

NOTE: the last command will create a global sc_ide preference file from a template. At the moment SuperCollider IDE can not use a local configuration file, but hopefully this will change in the future. Also note that if you cloned or moved this repository somewhere else than in your home directory you should edit the yaml file with `nano ~/.config/SuperCollider/sc_ide_conf.yaml` to make the paths in there point to your standalone directory.

startup
--

To run the full IDE first open a terminal window and type...

* `qjackctl`

Select the correct soundcard (under setup/interfaces) and then start jackd. _(if usb soundcard is used set periods to 3)_

Then open another terminal window and type...

* `cd supercolliderStandaloneRPI2`
* `export PATH=.:$PATH`
* `scide`

SuperCollider IDE should start and run like normal - with scope, meter, plot, gui, animation, help, quarks etc.

KNOWN ISSUES and TODO:

* help system and more is not working if scide is started via desktop icon - used to work
* hints are yellow and unreadable - will be fixed in the next version (3.9)
* 'libEGL warning: DRI2: failed to authenticate' that is posted in terminal at scide startup is harmless

autostart
--

* `sudo apt-get install xvfb`
* `crontab -e` #and add the following line to the end
* `@reboot cd /home/pi/supercolliderStandaloneRPI2 && xvfb-run ./autostart.sh`
* `sudo reboot` #and supercollider should automatically start after a while and play some beating sine tones.

Then edit the autostart script to load whichever file. By default it will load `mycode.scd`.

headless
--

To run sclang+scsynth only from ssh...

* `export DISPLAY=:0.0`
* `jackd -P75 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, and 1 the usb soundcard
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l sclang.yaml`

NOTE: one can also specify a .scd file to load when starting sclang like this: `./sclang -a -l sclang.yaml mycode.scd`

jessie-lite
--

The standalone also works under jessie-lite if the following additional steps are taken...

installation:

* `sudo apt-get install git libcwiid1 libasound2-dev libsamplerate0-dev libsndfile1-dev libreadline-dev xvfb`
* `git clone git://github.com/jackaudio/jack2.git --depth 1`
* `cd jack2`
* `./waf configure --alsa`
* `./waf build`
* `sudo ./waf install`
* `sudo ldconfig`
* `cd ..`
* `rm -rf jack2`
* `sudo nano /etc/security/limits.conf` #and add the following two lines at the end
  * `@audio - memlock 256000`
  * `@audio - rtprio 75`
* `nano supercolliderStandaloneRPI2/autostart.sh` #and change the jackd path to `/usr/local/bin/jackd`
* `sudo reboot`

startup:

* `jackd -P75 -dalsa -dhw:1 -p1024 -n3 -s -r44100 &` #edit -dhw to match your audio output. 0 is usually hdmi, and 1 the usb soundcard
* `cd supercolliderStandaloneRPI2`
* `xvfb-run --auto-servernum ./sclang -a -l sclang.yaml`
