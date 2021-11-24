#!/bin/bash
export QT_QPA_PLATFORM=offscreen
export JACK_NO_AUDIO_RESERVATION=1
#sleep 10 #optional - try with a little delay if autostart is not working
./sclang -a -l sclang.yaml mycode.scd
