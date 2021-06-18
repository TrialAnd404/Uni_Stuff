#!/usr/bin/env python


# import everything from lyricsGenre.py
import math
import sys, os, argparse
from collections import Counter, defaultdict
import pickle
import nltk
import pandas as pd
import re
import csv

"""
  Lyrics Classifier
  -------------------------
  a small interface for document classification. Implement your own Naive Bayes classifier 
  by completing the class NaiveBayesLyricsClassifier below.
"""



class NaiveBayesLyricsClassifier:


    def __init__(self):

        """ The classifier should store all its learned information
            in this 'model' object. Pick whatever form seems appropriate
            to you. Recommendation: use 'pickle' to store/load this model! """

        # self.model = None
        self.model = {}
        """
        # Dictionary{ Key: song-wort ; value: [(P(Ham), wort = 1),(P(Spam), wort = 1)]
        # alternatives model:
        # Dictionary{ Key: song wort ; value: Dictionary{ key: genre; value[P(0), P(1)]}}
        # dict[wort]            = [Hip-hop, Rock, Pop, ...]
        # self.model['bitches'] = [0.25, 0.1, 0.4, 0.4, ...]

        # iterieren alle worte + wahrscheinlichkeiten ausrechnen
        #                       --> bitch -> 70% hiphop 50% rock
        # auftreten wort im genre (z.b. 50 mal) / gesamtauftreten vom wort (z.b. 200 mal) -> 25%
        # (Anzahl Songs mit Wort / Songanzahl im Genre z.B. bitch in 30 von 50 Hip-Hop Songs -> 60%)
        """

        if os.path.isfile('model.pkl'):
            with open('model.pkl', 'rb') as file:
                self.model = pickle.load(file)

    # classifier.train(tokens_all_genres: token->amount, tokens_specific_genre: genre->token->amount])
    def train(self, features, labels):
        """
        trains a document classifier and stores all relevant
        information in 'self.model'.

        @type features: list< list<str> >
        @param features: Each entry in 'features' represents a document by its tokens.
                         'features' is of the form:

          [
            [ 'last', 'christmas', 'i', 'gave', 'you', ... ],               # doc 0
            [ 'just', 'a', 'smalltown', 'girl', ... ],                      # doc 1
            [ 'my', 'baby', 'dont', 'mess', 'around', 'because', ... ],     # doc 2
            ...
          ]

        @type labels: list<str>
        @param labels: 'labels' is a list of the same length as 'features'.
                       For each entry, it contains the class label of the respective
                       document.

          [
             'Pop',      # Label 0
             'Rock',     # Label 1
             'Hip-hop',  # Label 2
             ...
          ]

        """

        # labels = dictionary mit bekannten genres + worten mit nutzungshaeufigkeit
        # features = alle benutzten token mit nutzungshaeufigkeit

        model = defaultdict(dict)
        for label in labels.keys():
            model[label] = defaultdict(dict)
            for feature in features.keys():
                #print(labels.get(label).get(feature, 0) / features.get(feature))
                p1 = labels.get(label).get(feature, 0) / features.get(feature)
                p0 = 1-p1
                model[label][feature] = [p0, p1]

        #raise NotImplementedError()

        # store model
        with open('model.pkl', 'wb') as file:
            pickle.dump(model, file)

"""       
    def apply(self, features):
"""
"""
        applies a classifier to a set of documents. Requires the classifier
        to be trained (i.e., you need to call train() before you can call apply()).

        @type features: list< list<str> >
        @param features: Each entry in 'features' represents a document by its tokens.
                         'features' is of the form:

          [
            [ 'last', 'christmas', 'i', 'gave', 'you', ... ],               # doc 0 
            [ 'just', 'a', 'smalltown', 'girl', ... ],                      # doc 1
            [ 'my', 'baby', 'dont', 'mess', 'around', 'because', ... ],     # doc 2
            ...
          ]

        @rtype: list<str>
        @returns: a list of the same length as 'features'. 
                  For each entry, it contains the predicted label of the respective
                  document.

          [
             'Pop',      # Label 0
             'Rock',     # Label 1
             'Hip-hop',  # Label 2
             ...
          ]
"""
"""
        # FIXME: implement

        raise NotImplementedError()

        
"""
if __name__ == "__main__":

    # parse command line arguments (no need to touch)
    parser = argparse.ArgumentParser(description='A document classifier.')
    parser.add_argument('--train', help="train the classifier", action='store_true')
    parser.add_argument('--train2', help="train the classifier (alternative)", action='store_true')
    parser.add_argument('--apply', help="apply the classifier (you'll need to train or load"\
                                        "a trained model first)", action='store_true')
    parser.add_argument('--inspect', help="get some info about the learned model",
                        action='store_true')

    args = parser.parse_args()

    classifier = NaiveBayesLyricsClassifier()

    if args.train:
        # FIXME: implement
        # features,labels = ...
        # classifier.train(features, labels)
        # features aus lyrics dingsbums auslesen --> worte/wort-token aus gegebenem text
        # labels aus lyrics dingsbums auslesen --> genres
        genres = {}
        lyrics = []

        song_tokens = defaultdict(dict)  # einzelner song, token als key, anzahl als value
        genre_token = defaultdict(dict)  # genre als key, alle auftretenden token mit anzahl als value

        with open('train_small.csv') as csvdatei:
            songreader = csv.reader(csvdatei, delimiter=',')
            for row in songreader:
                # print(row[2], row[4])
                # nur adden, wenn beide einen akzeptierten value haben
                genre = row[2]
                lyric = row[4]

                tokenized = nltk.word_tokenize(lyric)
                print(tokenized)
                for token in tokenized:
                    token = token.lower()
                    if re.search("^[a-z]*$", token):
                        song_tokens[token] = lyrics_token.get(token, 0) + 1
                        # genre_token[genre][token] = genre_token.get(genre).get(token, 0) + 1
                        genre_token[genre][token] = genre_token[genre].get(token, 0) + 1

        #print(genre_token)

        sys.exit()

        # spalte 3: genre spalte 5: lyrics

        # Daten einlesen (geklaut aus Aufgabe 1)
        # data_csv = pd.read_csv("train_medium.csv", usecols=["Genre", "Lyrics"])
        # genres = data_csv['Genre']
        # genres_unique = set(genres)
        # lyrics = data_csv['Lyrics']
        # del lyrics[5828] # Hat keine Lyrics


        for lyric, genre in zip(lyrics, genres):
            tokenized = nltk.word_tokenize(lyric)

            for token in tokenized:
                token = token.lower()
                if re.search("^[a-z]*$", token):
                    lyrics_token[token] = lyrics_token.get(token, 0) + 1
                    #genre_token[genre][token] = genre_token.get(genre).get(token, 0) + 1
                    genre_token[genre][token] = genre_token[genre].get(token, 0) + 1

        # token_ges = genre_token[0] + genre_token[1] + ...
        # test output wie oft token insgesamt vorkommen
        #for token in lyrics_token.keys():
        #    print(token, lyrics_token.get(token))

        # test output wie oft token insgesamt vorkommen
        """
        for genre in genre_token.keys():
            print(genre)
            print("\n#############\n#############\n")
            print(genre_token.get(genre))
        """

        labels_test = genres_unique
        features_test = ["bitch", "love", "test", "Adrian"]
        #classifier.train(features_test, labels_test,)

        classifier.train(lyrics_token, genre_token)


        for genre in classifier.model.keys():
            for token in classifier.model[genre].keys():
                valueP0 = classifier.model.get(genre).get(token)[0]
                #if not (valueP0 == 1.0 or valueP0 == 0.0):
                print(genre, token, classifier.model.get(genre).get(token))

    if args.train2:
        # FIXME: implement
        # features,labels = ...
        # classifier.train(features, labels)
        # features aus lyrics dingsbums auslesen --> worte/wort-token aus gegebenem text
        # labels aus lyrics dingsbums auslesen --> genres
        genres = []

        tokens_all_genres = defaultdict(dict)  # token anzahl über alle genres
        tokens_specific_genre = defaultdict(dict)  # token anzahl in speziellen genres

        with open('train_small.csv') as csvdatei:
            songreader = csv.reader(csvdatei, delimiter=',')
            next(songreader)
            i = 0
            for row in songreader:

                # print(row[2], row[4])
                # nur adden, wenn beide einen akzeptierten value haben
                genre = row[2]
                genres.append(genre)
                lyric = row[4]
                tokenized_lyrics = set(nltk.word_tokenize(lyric.lower()))

                for lyric_token in tokenized_lyrics:
                    lyric_token = lyric_token.lower()
                    if re.search("^[a-z]*$", lyric_token):  #token gültigkeit prüfen, "," oder "n't" werden verworfen
                        # token verwendung über alle genres +1
                        tokens_all_genres[lyric_token] = tokens_all_genres.get(lyric_token, 0) + 1
                        # token verwendung in diesem genre +1
                        tokens_specific_genre[genre][lyric_token] = tokens_specific_genre[genre].get(lyric_token, 0) + 1
            """
            for genre in tokens_specific_genre.keys():
                for token in tokens_specific_genre[genre].keys():
                    print(genre, token, tokens_specific_genre[genre][token])

            for token in tokens_all_genres.keys():
                if tokens_all_genres[token] > 5:
                    print(token, tokens_all_genres[token])
            
            """




        classifier.train(tokens_all_genres, tokens_specific_genre)

        #print(classifier.model.keys())
        for genre in classifier.model.keys():
            for token in classifier.model[genre].keys():
                valueP0 = classifier.model.get(genre).get(token)[0]
                if not (valueP0 == 1.0 or valueP0 == 0.0):
                    print(genre, token, classifier.model.get(genre).get(token))

        #verify output for single word:
        testword = "sehn"
        for genre in classifier.model.keys():
            print(genre, testword, classifier.model.get(genre)[testword])
            print(genre, "Anzahl", tokens_specific_genre[genre].get(testword, 0))
        print("Gesamt: ", tokens_all_genres[testword])


    if args.apply:
        # FIXME: implement later
        #print("Todo")
        words_assumedMetal = ["heavy", "hell", "hard", "loud", "strong", "hate", "war", "sword"]
        words_assumedPop = ["love", "horizon", "you", "sweet", "dear", "loving"]

