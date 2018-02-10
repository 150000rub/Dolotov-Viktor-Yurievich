def alg(a, b):
    d = -1
    m = max(len(a), len(b))

    F = [[0] * (len(a) + 1) for i in range(len(b) + 1)]
    for i in range(1, len(a) + 1):
        F[0][i] = d * i
    for i in range(1, len(b) + 1):
        F[i][0] = d * i
    for i in range(1, len(b) + 1):
        for j in range(1, len(a) + 1):
            diag = 0
            if a[j - 1] == b[i - 1]:
                diag = 10
            M = F[i - 1][j - 1] + m * diag * len(b[i - 1])
            D = F[i - 1][j] + d
            I = F[i][j - 1] + d
            F[i][j] = max(M, D, I)

    res = []
    i = len(b)
    j = len(a)
    while i > 0 and j > 0:
        Score = F[i][j]
        ScoreLeft = F[i][j - 1]
        ScoreUp = F[i - 1][j]
        if Score == ScoreUp + d:
            i = i - 1
        else:
            if Score == ScoreLeft + d:
                j = j - 1
            else:
                if a[j - 1] == b[i - 1]:
                    res.append(a[j - 1])
                i = i - 1
                j = j - 1
    res.reverse()
    return res
