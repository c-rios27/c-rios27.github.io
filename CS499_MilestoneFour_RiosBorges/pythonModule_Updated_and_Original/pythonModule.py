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

    # Method to sanitize query
    def querySanitization(self, query, dict) -> dict:
        safeQuery = {}
        for key, value in query.items():
            if key.startswith("$"):
                continue
            if isinstance(value, dict):
                safeQuery[key] = self.querySanitization(value)
            else:
                safeQuery[key] = value
        return safeQuery

    # CRUD Implementation
    # Method to implement the C in CRUD
    def create(self, data):
        if data is not None:
            # Validate that data is dictionary
            # Data should be dictionary
            if isinstance(data, dict):
                # Create single animal profile
                self.database.animals.insert_one(data)
            elif isinstance(data, list):
                # Create multiple animal profiles
                self.database.animals.insert_many(data)
            else: raise Exception("Please enter a valid data parameter.")
        else:
            raise Exception("Nothing to save, data parameter is empty.")

    # Method to implement the R in CRUD
    def read(self, searchData):
        if searchData:
            # Query sanitization
            searchData = self.querySanitization(searchData)
            data = self.database.animals.find(searchData, {"_id": False})
        else:
            data = self.database.animals.find( {}, {"_id": False})
        return data

    # Method to implement the U in CRUD
    def update(self, searchData, updateData):
        if searchData is not None:
            # Query sanitization
            searchData = self.querySanitization(searchData)
            result = self.database.animals.update_many(searchData, {"$set": updateData})
        else:
            return "{}"
        return result.raw_result

    # Method to implement the D in CRUD
    def delete(self, deleteData):
        if deleteData is not None:
            # Query sanitization
            deleteData = self.querySanitization(deleteData)
            result = self.database.animals.delete_many(deleteData)
        else:
            return "{}"
        return result.raw_result

# End