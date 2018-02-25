import difflib


def diffalg(a, b):
    seq = difflib.SequenceMatcher()
    seq.set_seqs(a, b)
    string = []
    for block in seq.get_matching_blocks():
        ai = block[0]
        if block[2] != 0:
            for i in range(block[2]):
                string.append(a[ai+i])
    return string
