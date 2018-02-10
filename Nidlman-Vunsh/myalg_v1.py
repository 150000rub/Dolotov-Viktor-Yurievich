def alg(a, b):
    d = -1
    m = max(len(a[0]), len(b))

    F = [[0] * (len(a[0]) + 1) for i in range(len(b) + 1)]
    for i in range(1, len(a[0]) + 1):
        F[0][i] = d * i
    for i in range(1, len(b) + 1):
        F[i][0] = d * i * len(a)
    for i in range(1, len(b) + 1):
        for j in range(1, len(a[0]) + 1):
            diag = 0
            for k in range(len(a)):
                if a[k][j - 1] == b[i - 1]:
                    diag += 10
                else:
                    diag = 0
                    break
            M = F[i - 1][j - 1] + m * diag * len(b[i - 1])
            D = F[i - 1][j] + d * len(a)
            I = F[i][j - 1] + d
            F[i][j] = max(M, D, I)

    resA = [[] for i in range(len(a))]
    resB = []
    i = len(b)
    j = len(a[0])
    while i > 0 and j > 0:
        Score = F[i][j]
        ScoreDiag = F[i - 1][j - 1]
        ScoreLeft = F[i][j - 1]
        ScoreUp = F[i - 1][j]
        if Score == ScoreUp + d * len(a):
            resB.append(b[i - 1])
            for s in resA:
                s.append('-')
            i = i - 1
        else:
            if Score == ScoreLeft + d:
                for k in range(len(resA)):
                    resA[k].append(a[k][j - 1])
                resB.append('-')
                j = j - 1
            else:
                diag = 0
                for k in range(len(a)):
                    if a[k][j - 1] == b[i - 1]:
                        diag += 10
                    else:
                        diag = 0
                        break
                if Score == ScoreDiag + diag * m * len(b[i - 1]):
                    for k in range(len(resA)):
                        resA[k].append(a[k][j - 1])
                    resB.append(b[i - 1])
                    i = i - 1
                    j = j - 1
    while j > 0:
        for k in range(len(resA)):
            resA[k].append(a[k][j - 1])
        resB.append('-')
        j = j - 1
    while i > 0:
        resB.append(b[i - 1])
        for s in resA:
            s.append('-')
        i = i - 1

    res = []
    for s in resA:
        s.reverse()
        res.append(s)
    resB.reverse()
    res.append(resB)
    return res
