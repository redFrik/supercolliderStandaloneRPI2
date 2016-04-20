#!/bin/bash
/usr/bin/jackd -P95 -dalsa -dhw:1 -p1024 -n3 -s &
./sclang -a -l sclang.yaml mycode.scd
