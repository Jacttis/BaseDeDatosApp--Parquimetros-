
INSERT INTO conductores (nombre,apellido,telefono,dni,direccion,registro)VALUE ('Jaime','Barreto','2915121314',20050914,'Guatemala 323',1);
INSERT INTO conductores (nombre,apellido,telefono,dni,direccion,registro)VALUE ('Alex','Gonzalez','2915121315',20140914,'Guaracha 450',2);
INSERT INTO conductores (nombre,apellido,telefono,dni,direccion,registro)VALUE ('Juan','Velez','2915121312',20140916,'Argentina 452',3);
INSERT INTO conductores (nombre,apellido,telefono,dni,direccion,registro)VALUE ('Martin','Gundel','2915321316',20140918,'Avellaneda 412',4);
INSERT INTO conductores (nombre,apellido,telefono,dni,direccion,registro)VALUE ('Juan','Carlos','2915121317',20140919,'Oro 200',5);

INSERT INTO automoviles(marca, modelo, color, patente,dni) VALUE ('Chevrolet','Camaro','negro ','PIV518',20050914);
INSERT INTO automoviles(marca, modelo, color, patente,dni) VALUE ('Honda','Civic','rojo ','PIV519',20140914);
INSERT INTO automoviles(marca, modelo, color, patente,dni) VALUE ('Chevrolet','Spin','gris ','PIV520',20140916);
INSERT INTO automoviles(marca, modelo, color, patente,dni) VALUE ('Ford','Ranger','blanco ','PIV521',20140918);
INSERT INTO automoviles(marca, modelo, color, patente,dni) VALUE ('Dodge','Ram','marron ','PIV522',20140919);


INSERT INTO tipos_tarjeta(tipo, descuento) VALUE ('Premium',0.35);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUE ('Trabajor',0.10);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUE ('VIP',0.80);

INSERT INTO tarjetas(id_tarjeta, saldo,patente,tipo) VALUE (01,100.00,'PIV518','Premium');
INSERT INTO tarjetas(id_tarjeta, saldo,patente,tipo) VALUE (02,010.00,'PIV519','Trabajor');
INSERT INTO tarjetas(id_tarjeta, saldo,patente,tipo) VALUE (03,080.00,'PIV520','VIP');
INSERT INTO tarjetas(id_tarjeta, saldo,patente,tipo) VALUE (04,200.00,'PIV521','VIP');
INSERT INTO tarjetas(id_tarjeta, saldo,patente,tipo) VALUE (05,300.00,'PIV522','Trabajor');

INSERT INTO ubicaciones(calle, altura, tarifa) VALUE ('Zapiola',1100,20.00);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUE ('Colombia',1400,40.00);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUE ('Alem',1200,80.00);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUE ('Urquiza',3200,60.00);

INSERT INTO inspectores (legajo, dni, nombre, apellido, password) VALUE (1,41097481,'Axel', 'Fontana',md5('1234'));
INSERT INTO inspectores (legajo, dni, nombre, apellido, password) VALUE (2,41045612,'Tomas', 'Diez',md5('12345'));
INSERT INTO inspectores (legajo, dni, nombre, apellido, password) VALUE (3,41045613,'Julian', 'Acttis',md5('123456'));

INSERT INTO parquimetros(id_parq,numero,calle,altura) VALUE (10,1,'Zapiola',1100);
INSERT INTO parquimetros(id_parq,numero,calle,altura) VALUE (20,2,'Colombia',1400);
INSERT INTO parquimetros(id_parq,numero,calle,altura) VALUE (30,3,'Alem',1200);

INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUE (01,10,'2020-10-13','00:00:00',null,null);
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUE (02,20,'2020-10-14','00:00:00','2020-10-14','22:00:00');
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUE (04,20,'2020-10-25','00:00:00',null,null);

INSERT INTO accede(legajo,id_parq,fecha,hora) VALUE (1,10,'2020-10-09','12:50:10');
INSERT INTO accede(legajo,id_parq,fecha,hora) VALUE (2,20,'2020-11-08','14:30:33');
INSERT INTO accede(legajo,id_parq,fecha,hora) VALUE (2,20,'2020-11-28','14:30:33');


INSERT INTO asociado_con (legajo,calle,altura,dia,turno) VALUE (1,'Zapiola',1100,'Ma','M');
INSERT INTO asociado_con (legajo,calle,altura,dia,turno) VALUE (2,'Colombia',1400,'Lu','T');
INSERT INTO asociado_con (legajo,calle,altura,dia,turno) VALUE (3,'Urquiza',3200,'Mi','M');

INSERT INTO multa (numero, fecha, hora, patente, id_asociado_con) VALUE (11, '2020-05-08','16:25:50','PIV518',80);
INSERT INTO multa (numero, fecha, hora, patente, id_asociado_con) VALUE (22, '2020-06-07','13:28:52','PIV519',90);


