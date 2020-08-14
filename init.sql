CREATE SCHEMA ADIDAS;
SET search_path TO ADIDAS;

CREATE TABLE CATEGORIES (
	id bigint  PRIMARY KEY,
	name VARCHAR ( 50 ) NOT NULL,
	parent_id bigint,
	UNIQUE(name, parent_id),
	CONSTRAINT fk_parent_category
      FOREIGN KEY(parent_id) 
	  REFERENCES CATEGORIES(id)
);

CREATE TABLE PRODUCTS (
	id bigint  PRIMARY KEY,
	name VARCHAR ( 50 ) NOT NULL,
	category_id bigint  NOT NULL,
	price DOUBLE PRECISION NOT NULL,
	model_year int NOT NULL,
	image_path VARCHAR ( 100 ),
	UNIQUE(name, model_year),
	CONSTRAINT fk_category
      FOREIGN KEY(category_id) 
	  REFERENCES CATEGORIES(id)
);


INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (1, 'Shoes', null);
INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (2, 'Football', 1);
INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (3, 'Running', 1);
INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (4, 'Cycling', 1);
INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (5, 'Road', 4);
INSERT INTO CATEGORIES (ID, NAME, PARENT_ID) VALUES (6, 'MTB', 4);



INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (1, 'Ultraboost', 3, 100.00, 2018);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (2, 'Ultraboost', 3, 110.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (3, 'Ultraboost', 3, 120.00, 2020);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (4, 'Solarboost', 3, 110.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (5, 'Adizero Boston', 3, 110.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (6, 'Adizero Boston', 3, 110.00, 2020);

INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (7, 'Five ten impact', 6, 120.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (8, 'Five ten impact', 6, 120.00, 2020);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (9, 'Five ten hellcat', 5, 110.00, 2020);

INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (10, 'Copa', 2, 140.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (11, 'Copa', 2, 140.00, 2020);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (12, 'Predator', 2, 90.00, 2018);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (13, 'Predator', 2, 115.00, 2019);
INSERT INTO PRODUCTS (ID, name, category_id, price, model_year) VALUES (14, 'Predator', 2, 130.00, 2020);



