import numpy as np
import pandas as pd
from collections import defaultdict
import nltk

data_csv = pd.read_csv("train_small.csv", usecols=["Genre", "Lyrics"])

genres = np.array(data_csv['Genre'])
genres_unique = set(genres)

lyrics = data_csv['Lyrics']
word_list = np.array(lyrics.str.split(" "))[:1500] # Testweise die ersten x Werte
for i in range(len(word_list)):
    word_list[i] = set(np.array(word_list[i]))

word_dict = defaultdict(dict)

# Sehr, sehr langsam
for genre, row in zip(genres, word_list):
    for word in row:
        word_found = False
        word_dict[genre][word] = word_dict[genre].get(word, 0) + 1
        # for entry, amount in word_dict[genre].items():
        #     if entry == word:
        #         word_dict[genre][entry] += 1
        #         word_found = True
        #         break
        # if not word_found:
        #     word_dict[genre][word] = 1

word_dict_sorted = defaultdict(dict)
for genre in genres_unique:
    word_dict_sorted[genre] = sorted(word_dict[genre].items(), key=lambda item: item[1], reverse=True)

# Die Wörter sind überall ähnlich und nicht sehr aussagekräftig, z.B. "I", "a", "to"
for genre in genres_unique:
    print(genre, ":")
    for i, word in enumerate(word_dict_sorted[genre]):
        print(i, ": ", word)
        if i > 10: break # Nur Top 10