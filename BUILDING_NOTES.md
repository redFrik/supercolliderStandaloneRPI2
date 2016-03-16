#notes

Instructions for building these binaries and set up a similar standalone repository...

First build SuperCollider master on a RPi2 (or other Linux box) following the instructions in the ScIDE section [here](http://supercollider.github.io/development/building-raspberrypi.html). One can also use an existing sc install assuming all the files are in their default directories.

* `mkdir supercolliderStandaloneRPI2 && cd supercolliderStandaloneRPI2`
* `cp /usr/local/bin/sc* .` #this will copy scide, sclang and scsynth
* `cp -r /usr/local/lib/SuperCollider/plugins .` #copies all plugins
* `mkdir -p share/system`
* `cp -r /usr/local/share/SuperCollider/* share/system` #copies help, classes, examples etc
* `mkdir -p share/system/Extensions/SystemOverwrites`
* `nano share/system/Extensions/SystemOverwrites/extLinuxPlatform.sc`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/share/system/Extensions/SystemOverwrites/extLinuxPlatform.sc>
* `mkdir share/users`
* `nano share/users/archive.sctxar`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/share/user/archive.sctxar>
* `nano sclang.yaml`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/sclang.yaml>
* `nano start.sh`
* and paste the content from <https://raw.githubusercontent.com/redFrik/supercolliderStandaloneRPI2/master/start.sh>
* `chmod +x start.sh`

Now the folder supercolliderStandaloneRPI2 should contain what is needed to run ScIDE standalone (if started as in the README.md). Copy it to another machine with the same system and try.

publish
--

My own additional notes for this git repository...

* note which supercollider master commit was used in README.md
* note which raspbian image was used in README.md
* copy the files over to laptop...
  * `cd supercolliderStandaloneRPI2`
  * `scp pi@raspberrypi.local:supercolliderStandaloneRPI2/sc* .`
  * `rm -r plugins`
  * `scp -r pi@raspberrypi.local:supercolliderStandaloneRPI2/plugins .`
  * `rm -r share`
  * `scp -r pi@raspberrypi.local:supercolliderStandaloneRPI2/share .`
  * and possibly the yaml files as well if something changed
* git commit and sync
