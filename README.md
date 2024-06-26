# supercolliderStandaloneRPI2
Standalone for Raspberry Pi 2 and 3 including the full IDE

This is the audio synthesis program [SuperCollider](https://github.com/supercollider/supercollider) version 3.13.0 (branch main, commit 3188503, 19feb2023) + [sc3-plugins](https://github.com/supercollider/sc3-plugins) (branch main, commit cd37e2f, 24jan2023) compiled for **Raspberry Pi 2** and **Raspberry Pi 3** (and likely **Raspberry Pi 4** but this is untested).

The standalone was built on a RPi 3B using [this guide](https://github.com/supercollider/supercollider/blob/develop/README_RASPBERRY_PI.md) and tested to run under [Raspberry Pi OS](https://www.raspberrypi.com/software/operating-systems/) 32-bit with desktop (2022-09-22-raspios-bullseye-armhf) and Lite (2022-09-22-raspios-bullseye-armhf-lite).

For 32-bit Legacy (raspios buster, desktop and Lite) download the previous release [sc3.12.0](https://github.com/redFrik/supercolliderStandaloneRPI2/releases/tag/v1.9) and follow the instructions in that README instead.

For 64-bit (**RPi4, Zero2**...) use [this repository](https://github.com/redFrik/supercolliderStandaloneRPI64/).

For **Raspberry Pi 1** and **Raspberry Pi Zero** use [this repository](https://github.com/redFrik/supercolliderStandaloneRPI1).

A quick (4:33) screencast showing the installation in realtime can be seen here... https://vimeo.com/397466041

**Note** since SuperCollider version 3.10 the ScIDE needs to be built without qt-webengine (`-DSC_USE_QTWEBENGINE:BOOL=OFF`) and that means that the **built-in help system is not available** for this standalone. You can browse help files at [doc.sccode.org](https://doc.sccode.org) until this is resolved.

This standalone is self-contained and all files are in one directory (except for the sc_ide_conf.yaml file - see below). It can coexist with other programs using scsynth like Sonic Pi and can even run simultaneously (as long as Sonic Pi is started first).

installation
--

_(this assumes you have done all the usual initialisation... burned the disk image, booted, changed password, optionally enabled ssh)_

open the terminal on the RPi and type...

* `sudo apt-get update`
* `sudo apt-get install qjackctl`
* `git clone https://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `mkdir -p ~/.config/SuperCollider`
* `cp supercolliderStandaloneRPI2/sc_ide_conf_temp.yaml ~/.config/SuperCollider/sc_ide_conf.yaml`

**NOTE:** Double check the name in the last `cp` command. The resulting file must be called `sc_ide_conf.yaml`

This last `cp` command will create a global sc_ide preference file from a template. At the moment SuperCollider IDE can not use a local configuration file, but hopefully, this will change in the future. Also, note that if you cloned or moved this repository somewhere else than in your home directory you should edit the yaml file to make the paths in there point to your standalone directory.

**IMPORTANT:** If you have set another user name than `pi`, edit the two files `SuperColliderIDE.desktop` and `sc_ide_conf.yaml` to match your username.

* `nano ~/supercolliderStandaloneRPI2/SuperColliderIDE.desktop`
* `nano ~/.config/SuperCollider/sc_ide_conf.yaml`

startup
--

To run the full IDE first open a terminal window and type...

* `qjackctl`

Select the correct soundcard (under setup/interfaces) and then start jackd. _(if USB soundcard is used also set periods to 3)_

Then open another terminal window and type...

* `cd supercolliderStandaloneRPI2`
* `export PATH=.:$PATH`
* `scide`

or simply just double click the desktop icon. SuperCollider IDE should start and run like normal - with scope, meter, plot, GUI, animation, quarks etc.

The startup file is located in the subdirectory `share/user/` and extensions you can put in `share/user/Extensions/` (first create that directory if it does not exist).

KNOWN ISSUES:

* 'libEGL warning: DRI2: failed to authenticate' that is posted in the terminal at ScIDE startup is harmless
* 'Open startup file' and 'Open user support directory' menu selections do not open the right file/folder.

jack
--

If you start SuperCollider without having Jack already running (like when autostarting or running headless), Jack will automatically launch when you boot the server. The audio settings then used are found in the file...

* `nano ~/.jackdrc`

_(this file is created by qjackctl so if you never ran qjackctl you might need to create this file manually.)_

The recommended jack audio settings are...

* `/usr/bin/jackd -P75 -dalsa -dhw:0 -p1024 -n3 -s -r44100`

and to set up Jack to use an external USB sound card change `-dhw:0` to `-dhw:1` like this...

* `/usr/bin/jackd -P75 -dalsa -dhw:1 -p1024 -n3 -s -r44100`

NOTE: the internal soundcard volume is by default set low (40). type `alsamixer` in terminal and adjust the PCM volume to 85 with the arrow keys, ESC key exits.

autostart
--

* `crontab -e` #and add the following line to the end
  * `@reboot cd /home/pi/supercolliderStandaloneRPI2 && ./autostart.sh`
* `sudo reboot` #and supercollider should automatically start after a while and play some beating sine tones.

Then edit the autostart script to load whichever file. By default, it will load `mycode.scd`.

headless
--

To run sclang+scsynth only from ssh...

* `export QT_QPA_PLATFORM=offscreen`
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml`

NOTE: one can also specify a .scd file to load when starting sclang like this: `./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml mycode.scd`

- - -

Lite
==

The standalone also works under Raspberry Pi OS Lite but the installation process is a little bit different...

installation:

* `sudo apt-get update`
* `sudo apt-get install libqt5network5 libqt5printsupport5 git libasound2-dev libsamplerate0-dev libsndfile1-dev`
* `sudo apt remove '*jack*'`
* `git clone --branch master --single-branch https://github.com/jackaudio/jack2.git`
* `cd jack2`
* `./waf configure --alsa`
* `./waf build`
* `sudo ./waf install`
* `sudo ldconfig`
* `cd ..`
* `rm -rf jack2`
* `sudo sh -c "echo @audio - memlock 256000 >> /etc/security/limits.conf"`
* `sudo sh -c "echo @audio - rtprio 75 >> /etc/security/limits.conf"`
* `echo /usr/local/bin/jackd -P75 -p16 -dalsa -dhw:0 -r44100 -p1024 -n3 > ~/.jackdrc` #use -dhw:1 for USB soundcard
* `cd ~; git clone https://github.com/redFrik/supercolliderStandaloneRPI2 --depth 1`
* `sudo reboot`

startup:

* `export QT_QPA_PLATFORM=offscreen`
* `cd supercolliderStandaloneRPI2`
* `./sclang -a -l ~/supercolliderStandaloneRPI2/sclang.yaml`
