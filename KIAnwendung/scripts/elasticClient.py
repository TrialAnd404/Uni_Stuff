from elasticsearch import Elasticsearch, helpers
import jsonlines
import json
from dask import dataframe as dd

import sys

es = Elasticsearch()

def genData():
    with jsonlines.open('../raw/wikibase.jsonl',) as wikiBase:
        for line in wikiBase.iter():    
            yield{
                "_index" : "articles",
                "title" : line["title"],
                "doc_id" : line["doc_id"],
                "text" : line["text"],
            }

def initialize():

    settings = {
        "number_of_shards" : 1,
        "number_of_replicas" : 2,
        #STEMMING ANALYZER
        "analysis": {
			#Filters that didnt work cuz im stupid
			#"""
            #"filter": {
            #    "english_stop": {
            #        "type":       "stop",
            #        "stopwords":  "_english_" 
            #    },
            #    "english_keywords": {
            #        "type":       "keyword_marker",
            #        "keywords":   ["example"] 
            #    },
            #    "english_stemmer": {
            #        "type":       "stemmer",
            #        "language":   "english"
            #    },
            #    "english_possessive_stemmer": {
            #        "type":       "stemmer",
            #        "language":   "possessive_english"
            #    }
            #},
			########################
            "analyzer": {
                "my_analyzer": {
                    "tokenizer":  "standard",
                    "filter": [
						#Commented Filters, didnt work	
                        #"english_possessive_stemmer",
                        "lowercase",
                        #"english_stop",
                        #"english_keywords",
                        #"english_stemmer"
						##############################
						"my_stemmer"
                    ]
                }
            },
			"filter": {
                "my_stemmer": {
                    "type":       "stemmer",
                    "language":  "english" 
                },
            },

        } 
    }

    mappings = {
        "properties" : {
            "doc_id" : {
                "type" : "text", 
                "index" : "true"
            },
            "title" : {
                "type" : "text",
                "analyzer" : "my_analyzer",
            },
            "text" : {
                "type" : "text", 
                "analyzer" : "my_analyzer"
            }
        }
    }
    
    try:
        es.indices.create(
            index = "articles",
            settings = settings,
            mappings = mappings
        )    
        helpers.bulk(es, genData())

    except:
        print('Ressource already set up once')

def search(searchString, doExplain, silent, amount, returnMe):
    do_explain = False

    #print(searchString)
    my_query = {
        "multi_match" : {
            "query" : searchString,
            "fields" : [
                #only needed for multiple terms
                "title^2",
                "text"
            ]
        }
    }

    result =  es.search(index="articles", explain=doExplain, size=amount, query=my_query)

    for hit in result["hits"]["hits"]:
        score,doc = hit["_score"],hit["_source"]
        if not silent:
            print(score, doc["title"], doc["doc_id"])
        if do_explain:
            print(json.dumps(hit["_explanation"], indent=4, sort_keys=True))
    
    if returnMe:
        return result


def dump():
    res = es.search(index="articles", query={"match_all": {}})
    print("Got %d Hits:" % res['hits']['total']['value'])
    for hit in res['hits']['hits']:
        print("%(doc_id)s %(title)s: %(text)s" % hit["_source"])


def benchmark():

    #benchmarking set for precision
    precisions = [1,5,10,20,50,100]
    avgPrec = 0

    #read all questions
    df = dd.read_csv('../raw/train_sanitized_medium.csv', sep=';', engine='python')
    print("iterating %d questions" % len(df))
    
    #ask all questions and compare found ID with actual id
    for index, row in df.iterrows():
        totalPrecision = 0
        avgPrecisionQuery = 0

        currentQuestion = row['question']
        id = row['id']
        #print(type(id))
        #print("searching for %d" % id)
        #search for answer to question
        for precision in precisions:
            #print("checking top %d results" % precision)

            #currentSearch = search(currentQuestion, False, True, max(precisions), True)
            currentSearch = search(currentQuestion, False, True, precision, True)
        
            for hit in currentSearch["hits"]["hits"]:
                score,doc = hit["_score"],hit["_source"]
                #print(score, doc["title"], doc["doc_id"])
                #print("found this in list: ",doc["doc_id"])
                #print(type(doc["doc_id"]))
                if int(doc["doc_id"]) == id:
                    totalPrecision = totalPrecision + 1/precision
                    #print("found searched document! increasing precision by 1/%d" % precision)
                            
        avgPrecisionQuery = totalPrecision/len(precisions)
        #print("average precision for query: ", avgPrecisionQuery)
        avgPrec = avgPrec + avgPrecisionQuery

    avgPrec = avgPrec/len(df)
    print("total avg prec: ", avgPrec)

def dump():
    res = es.search(index="articles", query={"match_all": {}})
    print("Got %d Hits:" % res['hits']['total']['value'])
    for hit in res['hits']['hits']:
        print("%(doc_id)s %(title)s: %(text)s" % hit["_source"])


if sys.argv[1] == "-i":
    initialize()

if sys.argv[1] == "-s":
    if sys.argv[2] == "-b":
        benchmark()
    else:
        if sys.argv[2] == "-e":
            searchString = ""
            searchString += searchMe[3]
            for part in searchMe[4:]:
                searchString += " " + part
            search(searchString, True, False, 10, false)
        else:
            searchString = ""
            searchString += searchMe[2]
            for part in searchMe[3:]:
                searchString += " " + part
            search(searchString, False, False, 10, false)

if sys.argv[1] == "-d":
    dump()
