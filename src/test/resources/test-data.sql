INSERT INTO menu_orders(menu_order_id, name, price) VALUES (131L, 'menuTest',1200.00);

--INSERT INTO roles(role_id, names) VALUES(1L,'ROLE_USER');

INSERT INTO users(user_id, first_name, last_name, username, password, email, phonenumber, created_date,enabled)
VALUES (1L, 'testuser1', 'test1last', 'testing1', 'protatiNo09', 'testuser1@email.com', '07034786301', CURRENT_DATE , true);

INSERT INTO orders(order_id, user_id,created_date, total_price) VALUES(1L, 1, CURRENT_DATE,1900);

