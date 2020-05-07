#!/bin/python2
from yacryptopan import CryptoPAn
FILE="IP.csv"
cp = CryptoPAn('32-char-str-for-AES-key-and-pad.')
with open(FILE, "r") as input:
    flines = input.readlines()
    for plaintext in flines:
        plaintext = plaintext.strip()
        print "{},{}".format(plaintext,cp.anonymize(plaintext))
    
                    


