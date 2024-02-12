# --- !Ups
alter table post add index created_at (created_at);

# --- !Downs
alter table post drop index created_at on post;
