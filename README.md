# supercolliderStandaloneRPI2
Standalone for Raspberry Pi 2, 3, 4 with Raspbian including the full IDE

This is the audio synthesis program [SuperCollider](https://github.com/supercollider/supercollider) version 3.10.3 (branch 3.10, commit 39ed52c, 30aug2019) + [sc3-plugins](https://github.com/supercollider/sc3-plugins) (branch 3.10, commit 6d69ae9, 5mar2019) compiled for **Raspberry Pi 2** and **Raspberry Pi 3** (and likely **Raspberry Pi 4** but this is untested).

For **Raspberry Pi 1** and **Raspberry Pi Zero** use [this repository](https://github.com/redFrik/supercolliderStandaloneRPI1).

The standalone was built using [this guide](https://supercollider.github.io/development/building-raspberrypi.html) and tested to run under [Raspbian Buster with desktop](https://raspberrypi.org/downloads/raspbian/) (2019-07-10-raspbian-buster), Raspbian Buster Lite and under Raspbian Stretch (2019-04-08-raspbian-stretch).

Note since SuperCollider version 3.10 the ScIDE needs to be built without qt-webengine (`-DSC_USE_QTWEBENGINE:BOOL=OFF`) and that means that the **built-in help system is not available** for this standalone. You can browse help files at [doc.sccode.org](http://doc.sccode.org) until this is resolved.

This standalone is self-contained and all files are in one directory (except for the sc_ide_conf.yaml file - see below). It can coexist with other programs using scsynth like Sonic Pi and can even run simultaneously (as long as Sonic Pi is started first).

installation
--

_(this assumes you have done all the usual initialisation... burned the disk image, booted, changed password, optionally enabled ssh)_

open the terminal on the RPi and type...

* `sudo apt-get update`
* `sudo apt-get upgrade`
* `sudo apt-get dist-upgrade`
* `sudo apt-get install qjackctl libqt5quick5 libqt5opengl5`
* `git clone https://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir -p ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

NOTE: the last command will create a global sc_ide preference file from a template. At the moment SuperCollider IDE can not use a local configuration file, but hopefully this will change in the future. Also note that if you cloned or moved this repository somewhere else than in your home directory you should edit the yaml file with `nano ~/.config/SuperCollider/sc_ide_conf.yaml` to make the paths in there point to your standalone directory.

startup
--

To run the full IDE first open a terminal window and type...

* `qjackctl`

Select the correct soundcard (under setup/interfaces) and then start jackd. _(if usb soundcard is used also set periods to 3)_

Then open another terminal window and type...

* `cd supercolliderStandaloneRPI2`
* `export PATH=.:$PATH`
* `scide`

or simply just double click the desktop icon. SuperCollider IDE should start and run like normal - with scope, meter, plot, gui, animation, quarks etc.

The startupfile is located in the subdirectory `share/user/` and extensions you can put in `share/user/Extensions/` (first create that directory if it does not exist).

KNOWN ISSUES:

* 'libEGL warning: DRI2: failed to authenticate' that is posted in terminal at scide startup is harmless
* 'Open startup file' and 'Open user support directory' menu selections do not open the right file/folder.

jack
--

If you start SuperCollider without having Jack already running (like when autostarting or running headless), Jack will automatically launch when you boot the server. The audio settings then used are found in the file...

* `nano ~/.jackdrc`

_(this file is created by qjackctl so if you never ran qjackctl you might need to create this file manually.)_

The recommended jack audio settings are...

* `/usr/bin/jackd -P75 -dalsa -dhw:0 -p1024 -n3 -s -r44100`

and to set up Jack to use an external usb sound card change `-dhw:0` to `-dhw:1` like this...

* `/usr/bin/jackd -P75 -dalsa -dhw:1 -p1024 -n3 -s -r44100`

NOTE: the internal soundcard volume is by default set low (40). type `alsamixer` in terminal and adjust the pcm volume to 85 with the arrow keys, esc key exits.

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
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml`

NOTE: one can also specify a .scd file to load when starting sclang like this: `./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml mycode.scd`

- - -

raspbian-lite
==

The standalone also works under Raspbian Lite but the installation process is a little bit different...

installation:

* `sudo apt-get update`
* `sudo apt-get upgrade`
* `sudo apt-get dist-upgrade`
* `sudo apt-get install libqt5quick5 libqt5opengl5 libqt5printsupport5 libqt5sql5 git libasound2-dev libsamplerate0-dev libsndfile1-dev libreadline-dev xvfb`
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
* `nano ~/.jackdrc` #and add the following (use `-dhw:1` for usb soundcard)
  * `/usr/local/bin/jackd -P75 -dalsa -dhw:0 -r44100 -p1024 -n3`
* `cd ~; git clone https://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `sudo reboot`

startup:

* `cd supercolliderStandaloneRPI2`
* `xvfb-run --auto-servernum ./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml`
