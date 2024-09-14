# --- !Ups
alter table user add account_id   varchar(100) not null after id;
alter table user add email        varchar(256) not null after account_id;
alter table user add password     varchar(60)  not null after email;
alter table user add display_name varchar(100) not null after password;
alter table user add introduction varchar(100) not null after display_name;
alter table user add icon_url     varchar(256) after introduction;

# --- !Downs
alter table user
    drop column account_id,
    drop column email,
    drop column password,
    drop column display_name,
    drop column introduction,
    drop column icon_url;

