insert into ROOM (ROOM_ID, ROOM_DESCRIPTION, ROOM_NUMBER)
values (1, 'Biig room', '1A');
insert into ROOM (ROOM_ID, ROOM_DESCRIPTION, ROOM_NUMBER)
values (2, 'medium room', '2A');
insert into ROOM (ROOM_ID, ROOM_DESCRIPTION, ROOM_NUMBER)
values (3, 'Small room', '2b');

insert into CUSTOMER (CUSTOMER_ID, EMAIL_ADDRESS, NAME, SURNAME)
values (1, 'test@test.pl', 'mariusz', 'staniak');

insert into BOOK (BOOK_ID, STATUS, DATE_FROM, DATE_TO, CUSTOMER_ID, ROOM_ID)
values (1, 'CREATED', '2019-01-01', '2019-02-01', 1, 1);

insert into BOOK (BOOK_ID, STATUS, DATE_FROM, DATE_TO, CUSTOMER_ID, ROOM_ID)
values (2, 'CANCELLED', '2019-01-01', '2019-02-01', 1, 2);
