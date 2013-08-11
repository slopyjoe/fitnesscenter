# --- First database schema

# --- !Ups

create table person (
	id 				bigint not null,
	name			varchar(60),
	email			varchar(80),
	dob				timestamp,
	
	constraint pk_person primary key (id)
);

create sequence person_seq start with 1000;

create table client (
	id				bigint not null,
	emp_id			varchar(10),
	organization	varchar(5),
	person_id		bigint not null,
	
	constraint pk_client primary key (id))
;

create sequence client_seq start with 1000;

create table employee (
	id				bigint not null,
	role			varchar(15),
	person_id		bigint not null,
	
	constraint pk_employee primary key (id))
;

create sequence employee_seq start with 1000;

create table activity (
	id				bigint not null,
	name 			varchar(60),
	description		varchar(80),
	employee_id		bigint not null,
	constraint pk_activity primary key (id))
;

create sequence activity_seq start with 1000;

alter table client add constraint fk_client_person_1 foreign key (person_id) references person (id) on delete restrict on update restrict;
create index ix_client_person_1 on client (person_id);

alter table employee add constraint fk_employee_person_1 foreign key (person_id) references person (id) on delete restrict on update restrict;
create index ix_employee_person_1 on employee (person_id);

alter table activity add constraint fk_activity_employee_1 foreign key (employee_id) references employee (id) on delete restrict on update restrict;
create index ix_activity_employee_1 on activity (employee_id);

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists client;

drop table if exists employee;

drop table if exists activity;
 
drop table if exists client_count;

drop table if exists person;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists client_seq;

drop sequence if exists employee_seq;

drop sequence if exists person_seq;

drop sequence if exists activity_seq;