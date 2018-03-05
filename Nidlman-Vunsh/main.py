from diff_algorithm import diffalg
from myalg_v2 import alg

import time
import chardet
import re
import itertools

fIn = open('One.txt', "rb")
strIn = fIn.read()
fIn.close()

enc = chardet.detect(strIn).get("encoding")
strIn = strIn.decode(enc)
strIn = strIn.lower()
strings = strIn.split("\r\n\r\n")
fOut = open('Output.txt', "w")
maxnum = 120
m = 2
outlen = 10
for k in range(len(strings)):
    result = []
    instrings = strings[k].split(" +-?\r\n")
    workstrings = set(instrings)
    n = len(workstrings)
    ident = (1 - n / len(instrings)) * 100

    totalnum = n * (n - 1)
    while m <= n and totalnum < maxnum:
        totalnum *= n - m
        m += 1
    m -= 1

    words = [re.split(r' |\r\n', string) for string in workstrings]
    perms = list(itertools.permutations(words, m))
    algtime = time.time()
    for i in range(len(perms)):
        perm = list(perms[i])

        # Мой алгоритм
        res = alg(perm[0], perm[1])
        for j in range(2, m):
            res = alg(res, perm[j])
            if len(res) == 0:
                break
        for j in range(n):
            if not perm.__contains__(words[j]):
                res = alg(res, words[j])
            if len(res) == 0:
                break
        #
        # Обновление результата
        if (len(result) < len(res)) | ((len(result) == len(res)) & (len(result.__str__()) < len(res.__str__()))):
            result = res

        # SequenceMatcher
        res = diffalg(perm[0], perm[1])
        for j in range(2, m):
            res = diffalg(res, perm[j])
            if len(res) == 0:
                break
        for j in range(n):
            if not perm.__contains__(words[j]):
                res = diffalg(res, words[j])
            if len(res) == 0:
                break
        #
        # Обновление результата
        if (len(result) < len(res)) | ((len(result) == len(res)) & (len(result.__str__()) < len(res.__str__()))):
            result = res
    algtime = time.time() - algtime

    fOut.write("Total repetitions: " + len(instrings).__str__() +
               "\nIdentical repetitions: " + (len(instrings) - n).__str__() + " - " + ident.__str__() +
               "%\nTotal permutations: " + len(perms).__str__() +
               "\nTime: " + algtime.__str__() + " sec." +
               "\nResult: " + result.__str__() + "\n\n\n")
fOut.close()
