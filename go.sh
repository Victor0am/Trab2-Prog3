#!/bin/bash

ant clean
echo "Done!"

cp testes/01/in/*.csv .

ant compile

ant run


# ant run-read-only

# ant run-write-only

