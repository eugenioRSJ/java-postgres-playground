drop Table if EXISTS product;

CREATE Table if NOT EXISTS product(
    id SERIAL PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(45) NOT NULL,
    price DECIMAL NOT NULL
);