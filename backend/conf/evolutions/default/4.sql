# --- !Ups
alter table user
    add account_id   varchar(100) not null after id,
    add email        varchar(256) not null after account_id,
    add password     varchar(60)  not null after email,
    add display_name varchar(100) not null after password,
    add introduction varchar(100) not null after display_name,
    add icon_url     varchar(256) after introduction;

# --- !Downs
alter table user
    drop column account_id,
    drop column email,
    drop column password,
    drop column display_name,
    drop column introduction,
    drop column icon_url;

