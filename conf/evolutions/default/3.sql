# --- !Ups

create table client_count(
	client_id bigint not null,
	date_visited timestamp,
	count 	  int,
	constraint pk_client_count primary key (client_id, date_visited)
);


alter table client_count add constraint fk_count_client_1 foreign key (client_id) references client (id) on delete restrict on update restrict;
create index ix_client_count_1 on client_count (client_id);


insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 1);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 2);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 3);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 4);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 5);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 6);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 7);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 8);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 9);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 10);
insert into client_count (client_id, date_visited, count) values (1, '1984-01-24', 11);

# --- !Downs

delete from client_count;

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists client_count;
