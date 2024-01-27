# --- !Ups
create table post (
    id int unsigned not null auto_increment,
    user_id int unsigned not null,
    origin varchar(100) not null,
    location varchar(100),
    way_to_brew varchar(100),
    roast_level enum('light', 'cinnamon', 'medium', 'high', 'city', 'full_city', 'french', 'italian'),
    temperature int unsigned,
    grams_of_coffee int unsigned,
    grams_of_water int unsigned,
    grind_size enum('finest', 'fine', 'medium-fine', 'medium', 'coarse'),
    impression text,
    created_at datetime not null default current_timestamp,
    updated_at datetime not null default current_timestamp on update current_timestamp,
    primary key (id),
    foreign key fk_user_id (user_id) references user (id)
);

# --- !Downs
drop table post
