import mysql.connector
from mysql.connector import Error

def create_connection():
    try:
        connection=mysql.connector.connect(
            host=34.41.128.5 
        )