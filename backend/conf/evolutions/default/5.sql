# --- !Ups
alter table user
    add constraint unique_email unique (email),
    add constraint unique_account_id unique (account_id);

# --- !Downs
alter table user
    drop constraint unique_email,
    drop constraint unique_account_id;
