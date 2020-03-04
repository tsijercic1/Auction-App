
create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to root;

create table categories
(
	id bigint not null
		constraint categories_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	name varchar(255)
);

alter table categories owner to root;

create table roles
(
	id bigint not null
		constraint roles_pkey
			primary key,
	name varchar(60)
		constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
			unique
);

alter table roles owner to root;

create table subcategories
(
	id bigint not null
		constraint subcategories_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	name varchar(255),
	category_id bigint
		constraint fkiborb6ptvy1t1n3v6klb56l5s
			references categories
);

alter table subcategories owner to root;

create table users
(
	id bigint not null
		constraint users_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	email varchar(320)
		constraint uk6dotkott2kjsp8vw4d0m25fb7
			unique,
	gender varchar(255),
	name varchar(40),
	password varchar(100),
	phone_number varchar(255),
	profile_picture_url varchar(255),
	surname varchar(40),
	role_id bigint
		constraint fkp56c1712k691lhsyewcssf40f
			references roles
);

alter table users owner to root;

create table products
(
	id bigint not null
		constraint products_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	auction_end timestamp,
	auction_start timestamp,
	description varchar(255),
	name varchar(255),
	start_price numeric(19,2),
	seller_id bigint
		constraint fkbgw3lyxhsml3kfqnfr45o0vbj
			references users,
	subcategory_id bigint
		constraint fkappm930ygdfv4qkkhc05pbr5s
			references subcategories
);

alter table products owner to root;

create table bids
(
	id bigint not null
		constraint bids_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	amount numeric(19,2),
	bidder_id bigint
		constraint fkmtrc6tnwawlpk1u2km6qnxbha
			references users,
	product_id bigint
		constraint fkhtewr5n8ee2tlu0rj67d6y14p
			references products
);

alter table bids owner to root;

create table product_pictures
(
	id bigint not null
		constraint product_pictures_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	url varchar(255),
	product_id bigint
		constraint fk6rog63kl5t3e1b3u3vdpkc6n2
			references products
);

alter table product_pictures owner to root;

create table watches
(
	id bigint not null
		constraint watches_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	product_id bigint
		constraint fkpkyxjoa14h6a45gu07u8qlsr2
			references products,
	watcher_id bigint
		constraint fk9yirjrmybk1cpybnhu7314tne
			references users
);

alter table watches owner to root;

insert into roles
values (1, 'USER');

