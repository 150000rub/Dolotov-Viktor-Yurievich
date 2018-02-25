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
result = []
outlen = 10

#Первый запуск

#Мой алгоритм
time1 = time()
res = alg(words[0], words[1])
for i in range(len(words) - 2):
    res = alg(res, words[i + 2])
    if len(res) == 0:
        break
time1 = time() - time1

file_res = StringIO()
for word in res:
    file_res.write("%*s" % (outlen, word))
file_res.write("\n")
fOut.write(file_res.getvalue())
#

#Обновление результата
if (len(result) < len(res))|((len(result) == len(res))&(len(result.__str__()) < len(res.__str__()))):
    result = res

#SequenceMatcher
time2 = time()
res = diffalg(words[0], words[1])
for i in range(len(words) - 2):
    res = diffalg(res, words[i + 2])
    if len(res) == 0:
        break
time2 = time() - time2

file_res = StringIO()
for word in res:
    file_res.write("%*s" % (outlen, word))
file_res.write("\n")
fOut.write(file_res.getvalue())
#

#Обновление результата
if (len(result) < len(res))|((len(result) == len(res))&(len(result.__str__()) < len(res.__str__()))):
    result = res

#Вывод времени
fOut.write("My alg:%*f \t\tSequenceMatcher: %f\n\n" % (outlen, time1, time2))

#Конец первого запуска

#Второй запуск

#Мой алгоритм
time1 = time()
res = alg(words[1], words[0])
for i in range(len(words) - 2):
    res = alg(res, words[i + 2])
    if len(res) == 0:
        break
time1 = time() - time1

file_res = StringIO()
for word in res:
    file_res.write("%*s" % (outlen, word))
file_res.write("\n")
fOut.write(file_res.getvalue())
#

#Обновление результата
if (len(result) < len(res))|((len(result) == len(res))&(len(result.__str__()) < len(res.__str__()))):
    result = res

#SequenceMatcher
time2 = time()
res = diffalg(words[1], words[0])
for i in range(len(words) - 2):
    res = diffalg(res, words[i + 2])
    if len(res) == 0:
        break
time2 = time() - time2

file_res = StringIO()
for word in res:
    file_res.write("%*s" % (outlen, word))
file_res.write("\n")
fOut.write(file_res.getvalue())
#

#Обновление результата
if (len(result) < len(res))|((len(result) == len(res))&(len(result.__str__()) < len(res.__str__()))):
    result = res

#Обновление времени
fOut.write("My alg:%*f \t\tSequenceMatcher: %f\n\n" % (outlen, time1, time2))

#Конец второго запуска

fOut.write("Result: "+result.__str__())
fOut.close()
