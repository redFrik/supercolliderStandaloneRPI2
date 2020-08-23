#!/bin/bash
export QT_QPA_PLATFORM=offscreen
export JACK_NO_AUDIO_RESERVATION=1
./sclang -a -l sclang.yaml mycode.scd
