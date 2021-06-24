import numpy as np
import pandas as pd
from collections import defaultdict, Counter
import nltk
import re
import pickle

data_csv = pd.read_csv("train_small.csv", usecols=["Genre", "Lyrics"])

genres = data_csv['Genre']
genres_unique = set(genres)

lyrics = data_csv['Lyrics']
word_list = []
for lyric in lyrics:
    word_list.append(str(lyric).split())

print(word_list)

word_dict = defaultdict(dict)

for genre, row in zip(genres, word_list):
    for word in row:
        word_dict[genre][word] = word_dict[genre].get(word, 0) + 1

word_dict_sorted = defaultdict(dict)
for genre in genres_unique:
    word_dict_sorted[genre] = sorted(word_dict[genre].items(), key=lambda item: item[1], reverse=True)

#print(word_dict_sorted)

genre_words = defaultdict(dict)
# Die Wörter sind überall ähnlich und nicht sehr aussagekräftig, z.B. "I", "a", "to"
for genre in genres_unique:
    genre_words[genre] = 0
    #print(genre, ":")
    for i, word in enumerate(word_dict_sorted[genre]):
        print(i, ": ", word)
        if i >= 10: break # Nur Top 10

vokabular_token_total = defaultdict(dict)
vokabular_token_song = defaultdict(dict)

for lyric in lyrics:
    if (type(lyric) != float):
        lyrics_token = nltk.word_tokenize(lyric)
        #komplett über lyrics iterieren, lowercasen und im dictionary updaten
        for i in range(len(lyrics_token)):
            lyrics_token[i] = lyrics_token[i].lower()
            if re.search("^[a-z]*$", lyrics_token[i]):
                vokabular_token_total[lyrics_token[i]] = vokabular_token_total.get(lyrics_token[i], 0) + 1

        lyrics_token = set(lyrics_token)
        for token in lyrics_token:
            if re.search("^[a-z]*$", token):
                vokabular_token_song[token] = vokabular_token_song.get(token, 0) + 1

#vokabular_often_used = []
vokab_dictionary = defaultdict(dict)
#print(type(vokabular_token_song))
#print(type(vokabular_token_song))
for key in vokabular_token_song.keys():
    total = vokabular_token_total.get(key)
    songs = vokabular_token_song.get(key)
    if songs >= 5 and total >= 10:
        vokab_dictionary[key] = [songs, total]

#print(sorted(vokabular_often_used,key=lambda l:l[1]))
#print(len(vokabular_often_used))


#for key in vokab_dictionary.keys():
    #print(key, vokab_dictionary.get(key)[0], vokab_dictionary.get(key)[1])

