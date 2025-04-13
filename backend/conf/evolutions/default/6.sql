# --- !Ups
alter table user
    drop icon_url,
    add icon_image_key varchar(36) comment 'UUID v4' after introduction;

# --- !Downs
alter table user
    drop icon_image_key,
    add icon_url varchar(256) after introduction;
