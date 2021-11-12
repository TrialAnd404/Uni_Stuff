from elasticsearch import Elasticsearch, helpers
import jsonlines


es = Elasticsearch()

settings = {
    "number_of_shards" : 1 ,
    "number_of_replicas" : 2
}

mappings = {
        "properties" : {
        "house" : {"type" : "text" , "index" : "false" } ,
        "words" : {"type" : "text" , "index" : "true" }
    }
}


try:
	es.indices.create(
    	index = "test_index",
    	settings = settings,
    	mappings = mappings
	)
except:
	print("couldnt create ressource, progessing...")

def genData():
	
    docs = [
        {
            "house" : "Stark",
            "words" : "Winter is coming"
        },
        {
            "house" : "Greyjoy",
            "words" : "We do not sow"
        },
        {
            "house" : "Baratheon",
            "words" : "Ours is the fury"
        }
    ]

    for doc in docs:    
        yield{
            "_index" : "test_index",
            "house" : doc["house"],
			"words" : doc["words"],
        }
	
"""
	doc1 = {
		"house" : "ABCDEFG",
		"words" : "winter wihter afdfsad asdfasdf"
	}
	doc2 = {
		"house" : "ABCDEFG",
		"words" : "wanter wigter afdfsad asdfasdf"
	}
	doc3 = {
		"house" : "ABCDEFG",
		"words" : "wtnter witter afdfsad asdfasdf"
	}
	doc4 = {
		"house" : "ABCDEFG",
		"words" : "wznter wgnter asdf asdf"
	}
	
	es.index(index="test_index", document=doc1)
	es.index(index="test_index", document=doc2)
	es.index(index="test_index", document=doc3)
	es.index(index="test_index", document=doc4)
	
	es.indices.flush()
	es.indices.refresh()
"""
#genData()


helpers.bulk(es, genData())

es.indices.refresh()
my_query = {
    "match" : {
        "words" : {
			"query" : "winter is",
			"operator" : "or",
			"fuzziness" : 0,
		}
    }
}

result = es.search(index="test_index", size=10, query=my_query)

for hit in result["hits"]["hits"]:
	score,doc = hit["_score"], hit["_source"]
	print(score, doc["house"], doc["words"])

