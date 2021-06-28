#!/usr/bin/env python


# import everything from lyricsGenre.py
import math
import sys, os, argparse
from collections import Counter, defaultdict
import pickle
import nltk
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

        genres = labels
        lyrics = features

        genrecounter = defaultdict(dict) #wie oft song von bestimmtem genre
        tokens_all_genres = defaultdict(dict)  # token anzahl über alle genres
        tokens_specific_genre = defaultdict(dict)  # token anzahl in speziellen genres
        #vocabulary = defaultdict(dict)
        #genres = defaultdict(dict)

        model = defaultdict(dict)
        model2 = defaultdict(dict)

        songcount = 0

        for genre, lyrics in zip(genres, lyrics):
            songcount += 1
            model["genres"][genre] = model["genres"].get(genre, 0) + 1
            #print(lyrics)
            for lyric_token in lyrics:
                if re.search("^[a-z]*$", lyric_token):
                    # token verwendung über alle genres +1
                    tokens_all_genres[lyric_token] = tokens_all_genres.get(lyric_token, 0) +1
                    # token verwendung in diesem genre +1
                    tokens_specific_genre[genre][lyric_token] = tokens_specific_genre[genre].get(lyric_token, 0) + 1

        # labels = dictionary mit bekannten genres + worten mit nutzungshaeufigkeit
        # features = alle benutzten token mit nutzungshaeufigkeit
        for genre in model["genres"].keys():
            model["priors"][genre] = model["genres"][genre] / songcount

        print(model)
        #print(tokens_specific_genre)


        for genre in model["genres"].keys():

            for token in tokens_all_genres.keys():

                #print(labels.get(label).get(feature, 0) / features.get(feature))
                p1 = tokens_specific_genre[genre].get(token, 0) / model["genres"][genre]
                p0 = 1-p1
                model2[token][genre] = [p0, p1]


        model["vocabulary"]=model2
        print(model2)

        """
        #print(model2)

        for token in model["vocabulary"].keys():
            for genre in model["genres"].keys():
                if not(model["vocabulary"].get(token).get(genre)[0] == 1.0 or model["vocabulary"].get(token).get(genre)[0] == 0.0):
                    print(token, genre, model["vocabulary"][token][genre])


        #mini test mit shake

        #print(self.model)
        """
        testword = "shake"
        for genre in tokens_specific_genre.keys():
            print(genre, genrecounter[genre], tokens_specific_genre[genre].get(testword, 0))

        for genre in model["genres"].keys():
            print(genre, model["vocabulary"][testword].get(genre))

        # store model
        with open('model.pkl', 'wb') as file:
            pickle.dump(model, file)

    def apply(self, features):
        """
            applies a classifier to a set of documents. Requires the classifier
            to be trained (i.e., you need to call train() before you can call apply()).

            @type features: list< list<str> >
            @param features: Each entry in 'features' represents a document by its tokens.
                             'features' is of the form:

              [
                [Songtitel, [ 'last', 'christmas', 'i', 'gave', 'you', ... ]],               # doc 0
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

                # FIXME: implement
        
                raise NotImplementedError()
        

            features: (= songlyrics von verschiedenen songs)
                -> tokenizen, lowercasen, setten ---> jedes token kommt nur einmal vor        
            
            model:
                <token>:
                    Pop: [0,1]
                    ...
            ---> drüber iterieren, gucken ob in aktuellem song enthalten.
                ---> wenn ja, dann in jedem Genre in SongN -->  Genre + <token>[Genre][1],
                                                                sonst Genre + <token>[Genre][0]  
            -----------
            
            song1:
                Pop: log(0.2) + log(0.7) + ....
                Rock:   log(0.5) + log(0.6) + ...
                Country: log(0.2) + log(0.1) + ...
                ...
        """
        epsilon = 0.0001

        predictions = defaultdict(dict)
        for feature in features:
            currentSong = feature[2]

            for genre in self.model["genres"].keys():
                predictions[feature[0]][genre] = math.log10(self.model["priors"][genre])
                #default: priors addieren

            for token in self.model["vocabulary"].keys():
                if token in currentSong:
                    for genre in self.model["genres"].keys():
                        predictions[feature[0]][genre] += math.log10(max(self.model["vocabulary"][token][genre][1], epsilon))
                else:
                    for genre in self.model["genres"].keys():
                        predictions[feature[0]][genre] += math.log10(max(self.model["vocabulary"][token][genre][0], epsilon))

        # print(predictions)
        i = 0
        j = 0
        for song in predictions.keys():
        #     print(song, "actual genre: ", features[i][1], "calculated genre: ", max(predictions[song].items(), key=lambda x: x[1]))
            max(predictions[song].items(), key=lambda x: x[1])


        #     if features[i][1] == (max(predictions[song].items(), key=lambda x: x[1]))[0]:
        #         j += 1
        #     i += 1
        # print("Richtig Klassifiziert in %:", j/i*100)
        return (max(predictions[song].items(), key=lambda x: x[1]))[0]


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
        genres = []
        lyrics = []

        #with open('train_big/train.csv', encoding="utf-8") as csvdatei:
        with open('train_small.csv', encoding="utf-8") as csvdatei:
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
                lyrics.append(tokenized_lyrics)

        len(lyric)


        """
        for genre in tokens_specific_genre.keys():
            for token in tokens_specific_genre[genre].keys():
                print(genre, token, tokens_specific_genre[genre][token])

        for token in tokens_all_genres.keys():
            if tokens_all_genres[token] > 5:
                print(token, tokens_all_genres[token])
        
        """
        classifier.train(lyrics, genres)

    if args.apply:
        # FIXME: implement later
        features = []

        with open('test.csv', encoding="utf-8") as csvdatei:
            songreader = csv.reader(csvdatei, delimiter=',')
            next(songreader)

            rowcount = 0
            correct_count = 0
            for row in songreader:
                # print(row[2], row[4])
                # nur adden, wenn beide einen akzeptierten value haben
                genre = row[3]
                title = row[0]
                lyric = row[4]
                tokenized_lyrics = set(nltk.word_tokenize(lyric.lower()))
                # features.append((title, genre, tokenized_lyrics))
                rowcount += 1

                res = classifier.apply([(title, genre, tokenized_lyrics)])
                if genre == res:
                    correct_count += 1
                print("Genre | Programm:", genre, "|", res, "=>", (correct_count/rowcount) * 100, "%")

