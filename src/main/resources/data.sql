insert into vets(id, first_name, last_name, description, year) values(1, '진경', '강', '대한민국 최고의 애견 외과 수술 전문의', 4)
insert into vets(id, first_name, last_name, description, year) values(2, '준호', '양', '대한민국 최고의 애견 마취 전문의', 9)
insert into vets(id, first_name, last_name, description, year) values(3, '인숙', '호', '대한민국 최고의 파충류 전문의', 9)

insert into specialties values(1, '수술')
insert into specialties values(2, '마취')
insert into specialties values(3, '파충류')

insert into vet_specialties values (1, 1)
insert into vet_specialties values (2, 2)
insert into vet_specialties values (3, 3)