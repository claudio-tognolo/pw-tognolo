
#!/bin/bash

openssl genrsa -out baseKey.pem 2048
openssl pkcs8 -topk8 -inform PEM -in baseKey.pem -out privateKey.pem -nocrypt
openssl rsa -in baseKey.pem -pubout -outform PEM -out publicKey.pem
