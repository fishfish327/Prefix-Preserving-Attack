- generate cyphertext from IP.csv
```shell
cd yacryptopan
python2 generatecypher.py > result.txt
```
- generate cyphertext,plaintext pair from IP.csv
```shell
cd yacryptopan
python2 generatecypherplain.py > cypherplain.txt
```
- generate plaintext,cyphertext from IP.csv
```shell
cd yacryptopan
python2 generateplaincypher.py > plaincypher.txt
```

- how to compile java code
```shell
cd Attack/src/main
javac *.java
```
- how to clean java class file
```shell
cd Attack/src/main
rm *.class
```
- how to run cca Attack code
```shell
cd Attack/src/
java main.ChosenCipherAttack ../../yacryptopan/result.txt ../../yacryptopan/cypherplain.txt
```
- how to run cpa Attack code
```shell
cd Attack/src/
java main.ChosenPlainAttack ../../yacryptopan/result.txt 
```
