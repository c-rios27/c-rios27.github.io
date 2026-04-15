# Name: Carolyn Rios
# Course: CS-340
# Instructor: Tarik Iles

from pymongo import MongoClient
from bson.objectid import ObjectId

class AnimalShelter(object):
    """CRUD operations for Animal Collection in MongoDB"""
    def __init__(self):
        # Connection Variables
        USER = 'aacuser'
        PASS = 'SNHUpass'
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 31533
        DB = 'AAC'
        COL = 'animals'

        # Initialize Connection
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER, PASS, HOST, PORT))
        self.database = self.client['%s' % (DB)]
        self.collection = self.database['%s' % (COL)]

    # CRUD Implementation
    # Method to implement the C in CRUD
    def create(self, data):
        if data is not None:
            # Validate that data is dictionary
            # Data should be dictionary
            self.database.animals.insert_one(data)
        else:
            raise Exception("Nothing to save, data parameter is empty.")

    # Method to implement the R in CRUD
    def read(self, searchData):
        if searchData:
            data = self.database.animals.find(searchData, {"_id": False})
        else:
            data = self.database.animals.find( {}, {"_id": False})
        return data

    # Method to implement the U in CRUD
    def update(self, searchData, updateData):
        if searchData is not None:
            result = self.database.animals.update_many(searchData, {"$set": updateData})
        else:
            return "{}"
        return result.raw_result

    # Method to implement the D in CRUD
    def delete(self, deleteData):
        if deleteData is not None:
            result = self.database.animals.delete_many(deleteData)
        else:
            return "{}"
        return result.raw_result

# End