db = db.getSiblingDB('entries_management');

db.createCollection('entries');

db['entries'].createIndex({entryId_1_version_1: 1, version: 1}, {unique:true, name:'entryId_1_version_1'});