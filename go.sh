#!/bin/bash

ant clean
echo "Done!"

cp testes/01/in/*.csv entradas

ant compile

ant run_entradas

mv *.csv saidas


# ant run-read-only

# ant run-write-only

