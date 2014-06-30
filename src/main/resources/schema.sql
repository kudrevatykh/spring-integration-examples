create table quotes (id varchar(100), q_date timestamp, text CLOB(64 K),PRIMARY KEY (id));

create index q_date on quotes (q_date);