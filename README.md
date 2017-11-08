# bitcoin-go

## Indexes
* Say that pin.location is a geo info
* Say that nom is not string analyzed
 
```
PUT /bitcoin 
{
    "mappings" : {
        "bitcoin" : {
            "properties" : {
                "nom" : {
                    "type" : "string",
                    "index" : "not_analyzed" 
                }
                "pin": {
                    "properties": {
                        "location": {
                            "type": "geo_point"
                        }
                    }
                }
            }
        }
    }
}
```
