insert into country(id, country, date_created, last_updated) values (1, 'England', sysdate(), sysdate());
insert into country(id, country, date_created, last_updated) values (2, 'Scotland', sysdate(), sysdate());
insert into country(id, country, date_created, last_updated) values (3, 'Wales', sysdate(), sysdate());

insert into county(id, county, date_created, last_updated, country_id) values (1, 'Buckinghamshire', sysdate(), sysdate(), 1);
insert into county(id, county, date_created, last_updated, country_id) values (2, 'Midlothian', sysdate(), sysdate(), 2);
insert into county(id, county, date_created, last_updated, country_id) values (3, 'Glamorganshire', sysdate(), sysdate(), 3);

insert into city(id, city, date_created, last_updated, county_id) values (1, 'Milton Keynes', sysdate(), sysdate(), 1);
insert into city(id, city, date_created, last_updated, county_id) values (2, 'Edinburgh', sysdate(), sysdate(), 2);
insert into city(id, city, date_created, last_updated, county_id) values (3, 'Cardiff', sysdate(), sysdate(), 3);

insert into address(id, name, number, street1, street2, city_id, postcode, date_created, last_updated)
  values (1, 'The Willows', 1, 'Tree Lane', 'Tree Estate', 1, 'TR123EE', sysdate(), sysdate());
insert into address(id, name, number, street1, street2, city_id, postcode, date_created, last_updated)
  values (2, 'The River', 1, 'Canal Lane', 'Lake Estate', 2, 'RI123VR', sysdate(), sysdate());
insert into address(id, name, number, street1, street2, city_id, postcode, date_created, last_updated)
  values (3, 'The Beach', 1, 'Sandy Lane', 'Watery Estate', 3, 'BE123CH', sysdate(), sysdate());

insert into users(id, email, default_billing_address, default_shipping_address, date_created, last_updated)
  values(1, "will.hay@example.com", 1, 1, sysdate(), sysdate());
insert into users(id, email, default_billing_address, default_shipping_address, date_created, last_updated)
  values(2, "graham.moffatt@example.com", 2, 2, sysdate(), sysdate());
insert into users(id, email, default_billing_address, default_shipping_address, date_created, last_updated)
  values(3, "moore.marriott@example.com", 3, 3, sysdate(), sysdate());

insert into user_shipping_address(user_id, address_id) values (1, 1);
insert into user_shipping_address(user_id, address_id) values (1, 2);
insert into user_shipping_address(user_id, address_id) values (2, 2);
insert into user_shipping_address(user_id, address_id) values (2, 3);
insert into user_shipping_address(user_id, address_id) values (3, 3);
insert into user_shipping_address(user_id, address_id) values (3, 1);