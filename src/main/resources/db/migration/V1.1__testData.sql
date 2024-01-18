
set @ORG_ID = uuid();
INSERT INTO organization
(id, name, description, domain)
VALUES(@ORG_ID, 'Demo Org', 'Demo desc', 'demo-domain');

INSERT INTO forecast_dash_board
(id, organization_id, dash_board_id)
VALUES(uuid(), @ORG_ID, '');

INSERT INTO `user`
( id, first_name, last_name, email, password, role_name, organization_id)
VALUES(uuid(), 'Bhakta', 'Reddy', 'bhakta@ensarsolutions.com','$2a$10$xVRF.RqfSJcJzExzhVqFl.geliI3URbG4ZLmyz5amMwIXU2f.uG6a', 'ROLE_SUPER_ADMIN', @ORG_ID);

INSERT INTO `user`
( id, first_name, last_name, email, role_name, organization_id)
VALUES(uuid(), 'Kalyan', 'K', 'kalyan@ensarsolutions.com', 'ROLE_SUPER_ADMIN', @ORG_ID);

INSERT INTO `user`
( id, first_name, last_name, email, role_name, organization_id)
VALUES(uuid(), 'Sridhar', 'P', 'sridharg@ensarsolutions.com', 'ROLE_SUPER_ADMIN', @ORG_ID);

INSERT INTO `payment`
( id, name, number, email, address, bill_value, card_number, card_holder, date_value, cvc)
VALUES(1, 'John', '999999999', 'JohnDoe@gmail.com', 'New jersey, USA', 99, '123456789090', 'John Doe', '12/24', '111');



