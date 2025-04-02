#!/bin/bash

docker run -p 9000:9000 -d zadanie_2p
sleep 3
ngrok http 9000
