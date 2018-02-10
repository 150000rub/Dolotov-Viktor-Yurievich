from io import StringIO
from time import time

from diff_algorithm import diffalg
from myalg_v2 import alg

import chardet

fIn = open('Input3.txt', "rb")
strIn = fIn.read()
fIn.close()

enc = chardet.detect(strIn).get("encoding")
strIn = strIn.decode(enc)
strIn.lower()
strings = strIn.split("\r\n")
words = [string.split(" ") for string in strings]

fOut = open('Output.txt', "w")
outputLimit = 0;
outlen = 10

for j in range(len(words) - 1):
    c = words[0]
    words[0] = words[j]
    words[j] = c
    for k in range(j + 1, len(words)):
        c = words[1]
        words[1] = words[k]
        words[k] = c
        a = words[0]
        b = words[1]

        time1 = time()
        res = alg(a, b)
        for i in range(len(words) - 2):
            res = alg(res, words[i + 2])
        time1 = time() - time1

        file_res = StringIO()
        for word in res:
            file_res.write("%*s\n" % (outlen, word))
        fOut.write(file_res.getvalue())

        time2 = time()
        res = diffalg(a, b)
        for i in range(len(words) - 2):
            res = diffalg(res, words[i + 2])
        time2 = time() - time2

        outputLimit += 1
        fOut.write("My alg:%*f \t\tSequenceMatcher: %f\n" % (outlen, time1, time2))
        c = words[1]
        words[1] = words[k]
        words[k] = c
    c = words[0]
    words[0] = words[j]
    words[j] = c
    if (outputLimit >= 1000):
        break
fOut.close()
