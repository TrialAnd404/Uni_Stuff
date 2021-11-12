#!/bin/bash


echo "resetting elasticSearch Server ..."

curl -X DELETE 'http://localhost:9200/_all'

echo "Database deleted. setting up new database..."

python3 elasticClient.py i

echo "all done bye-bye :)"

