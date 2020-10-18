INSERT INTO category (name, type) VALUES ('Bills and Utilities', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Food and Drinks', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Health and Fitness', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Transportation', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Shopping', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Gifts & Donations', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Entertainment', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Education', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Household', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Others', 'EXPENSE');
INSERT INTO category (name, type) VALUES ('Salary', 'INCOME');
INSERT INTO category (name, type) VALUES ('Gifts', 'INCOME');
INSERT INTO category (name, type) VALUES ('Interest Money', 'INCOME');
INSERT INTO category (name, type) VALUES ('Selling', 'INCOME');
INSERT INTO category (name, type) VALUES ('Others', 'INCOME');

INSERT INTO transaction (amount, description, date, category_id) VALUES
    ('205.50', 'Monthly electricity bill', '2020-08-20', 1),
    ('185.00', 'Weekly groceries', '2020-08-21', 2),
    ('145.00', 'Internet bill', '2020-08-22', 1),
    ('9.00', 'Taxi ride', '2020-08-22', 1);