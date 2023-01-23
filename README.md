Application coded in Java17
Runs on port 8085, can be changed in application.properties


Attached postman collection tests

Command line 

curl -v http://localhost:8085/teletrac/nocontent -H 'Content-Type: application/json' -H 'Authorization: Bearer testToken'  -d '{ "RecordType": "xxx", "DeviceId": "357370040159770", "EventDateTime": "2014-05-12T05:09:48Z",   "FieldA": 68,   "FieldB": "xxx",   "FieldC": 123.45}' 

curl -v http://localhost:8085/teletrac/echo -H 'Content-Type: application/json' -H 'Authorization: Bearer testToken'  -d '{ "RecordType": "xxx", "DeviceId": "357370040159770", "EventDateTime": "2014-05-12T05:09:48Z",   "FieldA": 68,   "FieldB": "xxx",   "FieldC": 123.45}' 

curl -v http://localhost:8085/teletrac/device -H 'Content-Type: application/json' -H 'Authorization: Bearer testToken'  -d '{ "RecordType": "xxx", "DeviceId": "357370040159770", "EventDateTime": "2014-05-12T05:09:48Z",   "FieldA": 68,   "FieldB": "xxx",   "FieldC": 123.45}' 
