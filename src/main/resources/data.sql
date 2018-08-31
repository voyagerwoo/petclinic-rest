/* insert admin */
insert into user (id, username, email, password, active) values (1, 'admin', 'admin@email.com', '$2a$15$9PcBQaj9dlgunImIlufyDOOgOPenZufyFh9t9GQNqfGHm8csNZixG', true)
insert into user_roles (user_id, roles) values (1, 'ROLE_ADMIN')

/* insert vets */
insert into user (id, username, email, password, active) values (2, 'kang', 'kang@email.com', '$2a$15$9PcBQaj9dlgunImIlufyDOOgOPenZufyFh9t9GQNqfGHm8csNZixG', true)
insert into user_roles (user_id, roles) values (2, 'ROLE_VET')
insert into user (id, username, email, password, active) values (3, 'yang', 'yang@email.com', '$2a$15$9PcBQaj9dlgunImIlufyDOOgOPenZufyFh9t9GQNqfGHm8csNZixG', true)
insert into user_roles (user_id, roles) values (3, 'ROLE_VET')
insert into user (id, username, email, password, active) values (4, 'ho', 'ho@email.com', '$2a$15$9PcBQaj9dlgunImIlufyDOOgOPenZufyFh9t9GQNqfGHm8csNZixG', true)
insert into user_roles (user_id, roles) values (4, 'ROLE_VET')
insert into user (id, username, email, password, active) values (5, 'woo', 'woo@email.com', '$2a$15$9PcBQaj9dlgunImIlufyDOOgOPenZufyFh9t9GQNqfGHm8csNZixG', true)
insert into user_roles (user_id, roles) values (5, 'ROLE_VET')

/* insert users */



insert into vet(id, user_id, first_name, last_name, description, year) values(1, 2, '진경', '강', '대한민국 최고의 애견 외과 수술 전문의', 4)
insert into vet(id, user_id, first_name, last_name, description, year) values(2, 3, '준호', '양', '대한민국 최고의 애견 마취 전문의', 9)
insert into vet(id, user_id, first_name, last_name, description, year) values(3, 4, '인숙', '호', '대한민국 최고의 파충류 전문의', 9)
insert into vet(id, user_id, first_name, last_name, description, year) values(4, 5, '여명', '우', '대한민국 최고의 대형 포유류 전문의', 5)


insert into specialty values(1, '수술')
insert into specialty values(2, '마취')
insert into specialty values(3, '파충류')
insert into specialty values(4, '대형 포유류')

insert into vet_specialties values (1, 1)
insert into vet_specialties values (2, 2)
insert into vet_specialties values (3, 3)
insert into vet_specialties values (4, 1)
insert into vet_specialties values (4, 4)