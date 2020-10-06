INSERT INTO category VALUES (1, 'Bills and Utilities', 'EXPENSE');
INSERT INTO category VALUES (2, 'Food and Drinks', 'EXPENSE');
INSERT INTO category VALUES (3, 'Health and Fitness', 'EXPENSE');
INSERT INTO category VALUES (4, 'Transportation', 'EXPENSE');
INSERT INTO category VALUES (5, 'Shopping', 'EXPENSE');
INSERT INTO category VALUES (6, 'Gifts & Donations', 'EXPENSE');
INSERT INTO category VALUES (7, 'Entertainment', 'EXPENSE');
INSERT INTO category VALUES (8, 'Education', 'EXPENSE');
INSERT INTO category VALUES (9, 'Household', 'EXPENSE');
INSERT INTO category VALUES (10, 'Others', 'EXPENSE');
INSERT INTO category VALUES (11, 'Salary', 'INCOME');
INSERT INTO category VALUES (12, 'Gifts', 'INCOME');
INSERT INTO category VALUES (13, 'Interest Money', 'INCOME');
INSERT INTO category VALUES (14, 'Selling', 'INCOME');
INSERT INTO category VALUES (15, 'Others', 'INCOME');

INSERT INTO transaction (amount, description, date, category_id) VALUES
    ('205.50', 'Monthly electricity bill', '2020-08-20', 1),
    ('185.00', 'Weekly groceries', '2020-08-21', 2),
    ('145.00', 'Internet bill', '2020-08-22', 1),
    ('9.00', 'Taxi ride', '2020-08-22', 1);