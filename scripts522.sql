create database car
    with owner student;
create table cars
(
    id    serial primary key ,
    brand text not null,
    model text not null,
    price int
);
create table people
(
    id      serial primary key,
    name    text not null,
    age     int check ( age > 0 ),
    license boolean,
    car_id  int references cars (id)
);