create table item
(
    seqNo        bigint auto_increment comment '일련번호' primary key,
    brandCode    char(1)      not null comment '브랜드코드',
    categoryType smallint     not null,
    price         integer      not null,
    createAt     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '등록일시',
    updateAt     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '수정일시'
);

create index idx_item_category_type_price
    on item (categoryType, price asc);

