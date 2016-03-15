notes
--

notes on how to build these binaries and set up a standalone repository...

first build sc master on a rpi2 following the instructions in the scide section here <http://supercollider.github.io/development/building-raspberrypi.html>. one can also use an existing sc install assuming all the files are in their default linux directories.

* `mkdir supercolliderStandaloneRPI2 && cd supercolliderStandaloneRPI2`
* `cp /usr/local/bin/sc* .` #this will copy scide, sclang and scsynth
* `cp -r /usr/local/lib/SuperCollider/plugins .` #copy all plugins
* `mkdir -p share/system`
* `cp -r /usr/local/share/SuperCollider/* share/system` #copy help, classes, examples etc
* `mkdir share/system/Extensions`
* `nano share/system/Extensions/extLinuxPlatform.sc`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/share/system/Extensions/extLinuxPlatform.sc>
* `nano sclang.yaml`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/sclang.yaml>
* `nano start.sh`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/start.sh>
* `chmod +x start.sh`

publish
--

my own additional notes for this git repro...

* note which supercollider master commit used in readme.md
* copy the files over to laptop...
  * `cd supercolliderStandaloneRPI2`
  * `scp pi@raspberrypi.local:supercolliderStandaloneRPI2/sc* .`
  * `rm -r plugins`
  * `scp -r pi@raspberrypi.local:supercolliderStandaloneRPI2/plugins .`
  * `rm -r share`
  * `scp -r pi@raspberrypi.local:supercolliderStandaloneRPI2/share .`
  * and possibly the yaml and start files as well if something changed
* git commit and sync
