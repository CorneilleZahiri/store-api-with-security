create table cart(
id binary(16) primary key not null default(uuid_to_bin(uuid())),
date_created timestamp default(current_timestamp()));

create table cart_item(
id bigint primary key auto_increment not null,
cart_id binary(16) not null,
product_id bigint not null,
quantity int not null check(quantity >= 0),
constraint fk_cart foreign key(cart_id) references cart(id) on delete cascade,
constraint fk_product foreign key(product_id) references products(id) on delete cascade,
constraint unique_cart_product unique (cart_id, product_id)
);