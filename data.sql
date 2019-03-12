--
-- File generated with SQLiteStudio v3.1.1 on Sat Jul 28 10:09:05 2018
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: manager
CREATE TABLE manager (id INTEGER PRIMARY KEY UNIQUE NOT NULL, username STRING UNIQUE NOT NULL, password STRING UNIQUE NOT NULL);

-- Table: tables_data
CREATE TABLE tables_data (table_id INTEGER PRIMARY KEY UNIQUE NOT NULL, time DATE NOT NULL, price_on_table INTEGER NOT NULL);

-- Table: tables_drinks
CREATE TABLE tables_drinks (drinks_col INTEGER PRIMARY KEY UNIQUE NOT NULL, drink_name STRING NOT NULL, drink_price INTEGER NOT NULL);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
