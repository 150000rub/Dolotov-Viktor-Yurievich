import difflib


def diffalg(a, b):
    seq = difflib.SequenceMatcher()
    seq.set_seqs(a, b)
    string = []
    for block in seq.get_matching_blocks():
        ai = block[0]
        if block[2] != 0:
            string.append(a[ai])
        elif len(string) == 0 or string[len(string) - 1] != a[ai - 1]:
            string.append(a[ai - 1])
    return string
