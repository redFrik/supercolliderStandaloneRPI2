notes
--

on how to build these binaries and set up this standalone repository...

* build scide on a rpi2 following the scide section here <http://supercollider.github.io/development/building-raspberrypi.html>
* `mkdir supercolliderStandaloneRPI2 && cd supercolliderStandaloneRPI2`
* `cp /usr/local/bin/sc* .` #this will copy scide, sclang and scsynth
* `cp -r /usr/local/lib/SuperCollider/plugins .` #copies all plugins
* `mkdir -p share/system`
* `cp -r /usr/local/share/SuperCollider/* share/system` #copies help, classes, examples etc
* `mkdir -p share/user/Extensions`
* `nano share/user/Extensions/extLinuxPlatform.sc`
* and paste the following...
```
+ LinuxPlatform {
    startup {
        
        helpDir = this.systemAppSupportDir++"/Help";
        
        // Server setup
        Server.program = "exec ./scsynth";  //f0 edited
        Server.default.options.ugenPluginsPath = "plugins";  //f0 added
        
        // Score setup
        Score.program = Server.program;
        
        // default jack port hookup
        "SC_JACK_DEFAULT_INPUTS".setenv("system");
        "SC_JACK_DEFAULT_OUTPUTS".setenv("system");
        
        // automatically start jack when booting the server
        // can still be overridden with JACK_NO_START_SERVER
        "JACK_START_SERVER".setenv("true");
        
        // load user startup file
        this.loadStartupFiles;
    }
}
```
* `nano sclang.yaml`
* and paste the following...
```
includePaths:
    - ./share/system/SCClassLibrary
    - ./share/user/Extensions
postInlineWarnings: false
```
* `nano run.sh`
* and paste the following...
```
sclang -a -l sclang.yaml mycode.scd
```
* `chmod +x run.sh`


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
* git commit and sync
