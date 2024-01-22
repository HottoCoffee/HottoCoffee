# --- !Ups
create table user (
    id int unsigned not null auto_increment,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp,
    primary key (id)
);

# --- !Downs
drop table user
