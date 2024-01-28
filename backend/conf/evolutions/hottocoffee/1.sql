# --- !Ups
create table user (
    id int unsigned not null auto_increment,
    primary key (id)
);

# --- !Downs
drop table user
