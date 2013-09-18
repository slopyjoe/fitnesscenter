# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table activity (
  internal_id               bigint not null,
  description               varchar(255),
  instructor_email          varchar(255),
  has_schedule              boolean,
  default_activity          boolean,
  name                      varchar(255),
  constraint pk_activity primary key (internal_id))
;

create table bulletin_post (
  internal_id               bigint not null,
  message                   varchar(255),
  image_path                varchar(255),
  name                      varchar(255),
  constraint pk_bulletin_post primary key (internal_id))
;

create table fitness_schedule (
  internal_id               bigint not null,
  activity_internal_id      bigint,
  time_slot                 timestamp,
  difficulty                varchar(255),
  constraint pk_fitness_schedule primary key (internal_id))
;

create table member (
  email                     varchar(255) not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  organization              varchar(255),
  employee_id               varchar(255),
  dob                       timestamp,
  last_logged_in            timestamp,
  created_on                timestamp,
  active                    boolean,
  constraint pk_member primary key (email))
;

create table member_count (
  internal_id               bigint not null,
  timestamp                 timestamp,
  activity_internal_id      bigint,
  member_email              varchar(255),
  constraint pk_member_count primary key (internal_id))
;

create sequence activity_seq;

create sequence bulletin_post_seq;

create sequence fitness_schedule_seq;

create sequence member_seq;

create sequence member_count_seq;

alter table activity add constraint fk_activity_instructor_1 foreign key (instructor_email) references member (email) on delete restrict on update restrict;
create index ix_activity_instructor_1 on activity (instructor_email);
alter table fitness_schedule add constraint fk_fitness_schedule_activity_2 foreign key (activity_internal_id) references activity (internal_id) on delete restrict on update restrict;
create index ix_fitness_schedule_activity_2 on fitness_schedule (activity_internal_id);
alter table member_count add constraint fk_member_count_activity_3 foreign key (activity_internal_id) references activity (internal_id) on delete restrict on update restrict;
create index ix_member_count_activity_3 on member_count (activity_internal_id);
alter table member_count add constraint fk_member_count_member_4 foreign key (member_email) references member (email) on delete restrict on update restrict;
create index ix_member_count_member_4 on member_count (member_email);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists activity;

drop table if exists bulletin_post;

drop table if exists fitness_schedule;

drop table if exists member;

drop table if exists member_count;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists activity_seq;

drop sequence if exists bulletin_post_seq;

drop sequence if exists fitness_schedule_seq;

drop sequence if exists member_seq;

drop sequence if exists member_count_seq;

