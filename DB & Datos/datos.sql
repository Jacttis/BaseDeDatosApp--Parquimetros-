USE parquimetros;


#---------------------------------------------------------------------#
#INSERCIONES:

#-------------------------Conductores----------------------------------#

INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (1,'nombrecond1','apellidocond1','dire1',02914500001,11);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (2,'nombrecond2','apellidocond2','dire2',02914500002,22);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (3,'nombrecond3','apellidocond3','dire3',02914500003,33);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (4,'nombrecond4','apellidocond4','dire4',02914500004,44);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (5,'nombrecond5','apellidocond5','dire5',02914500005,55);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (6,'nombrecond6','apellidocond6','dire6',02914500006,66);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (7,'nombrecond7','apellidocond7','dire7',02914500007,77);
INSERT INTO Conductores(dni,nombre,apellido,direccion,telefono,registro)
VALUES (8,'nombrecond8','apellidocond8','dire8',02914500008,88);


#-------------------------Automoviles----------------------------------#

# Dos autos del conductor 1
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA111','marca1','modelo1','color1',1);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA112','marca1','modelo1','color1',1);

INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA222','marca1','modelo1','color2',2);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA333','marca3','modelo3','color3',3);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA444','marca4','modelo4','color4',4);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA555','marca5','modelo5','color5',5);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA666','marca6','modelo6','color6',6);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA777','marca7','modelo7','color7',7);
INSERT INTO Automoviles(patente,marca,modelo,color,dni)
VALUES ('AAA888','marca8','modelo8','color8',8);


#-------------------------tipos_tarjeta----------------------------------#

INSERT INTO tipos_tarjeta(tipo,descuento) VALUES ('normal',0);
INSERT INTO tipos_tarjeta(tipo,descuento) VALUES ('preferencial',0.1);
INSERT INTO tipos_tarjeta(tipo,descuento) VALUES ('vip',0.2);

#-------------------------tarjetas----------------------------------#

# Dos tarjetas para el auto AAA111
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (1,100,'normal','AAA111');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (11,100,'vip','AAA111');

INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (12,100,'vip','AAA112');

INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (2,100,'vip','AAA222');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (3,100,'preferencial','AAA333');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (4,100,'normal','AAA444');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (5,100,'preferencial','AAA555');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (6,100,'vip','AAA666');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (7,100,'normal','AAA777');
INSERT INTO tarjetas(id_tarjeta,saldo,tipo,patente) VALUES (8,100,'preferencial','AAA888');

#-------------------------Inspectores----------------------------------#

# un inspector para cada dia de la semana para la ubicación mitre al 100
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (1,11,'nombreins1','apellidoins1',md5('1'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (2,12,'nombreins2','apellidoins2',md5('2'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (3,13,'nombreins3','apellidoins3',md5('3'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (4,14,'nombreins4','apellidoins4',md5('4'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (5,15,'nombreins5','apellidoins5',md5('5'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (6,16,'nombreins6','apellidoins6',md5('6'));
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (7,17,'nombreins7','apellidoins7',md5('7'));

# inspector comodin: asociado a la ubicacion mitre al 200 todos los dias y turnos
INSERT INTO Inspectores(legajo,dni,nombre,apellido,password)
VALUES (10,20,'nombreins10','apellidoins10',md5('10'));


#-------------------------Ubicaciones----------------------------------#

INSERT INTO Ubicaciones(calle,altura,tarifa) VALUES ('Mitre', 100, 1.00);
INSERT INTO Ubicaciones(calle,altura,tarifa) VALUES ('Mitre', 200, 2.00);
INSERT INTO Ubicaciones(calle,altura,tarifa) VALUES ('Alem', 300, 3.00);
INSERT INTO Ubicaciones(calle,altura,tarifa) VALUES ('Alem', 400, 4.00);

#-------------------------Parquimetros----------------------------------#

INSERT INTO Parquimetros(id_parq,numero,calle,altura) VALUES (101,101,'Mitre',100);

INSERT INTO Parquimetros(id_parq,numero,calle,altura) VALUES (201,201,'Mitre',200);

# dos parquimetros en Alem al 300
INSERT INTO Parquimetros(id_parq,numero,calle,altura) VALUES (301,301,'Alem',300);
INSERT INTO Parquimetros(id_parq,numero,calle,altura) VALUES (302,302,'Alem',300);

INSERT INTO Parquimetros(id_parq,numero,calle,altura) VALUES (401,401,'Alem',400);

#-------------------------Estacionamientos---------------------------------#

# Un estacionamiento cerrado ayer para cada parquimetro
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (4,101,date_sub(curdate() , interval 1 day),'10:00:00',date_sub(curdate() , interval 1 day),'11:00:00');
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (5,201,date_sub(curdate() , interval 1 day),'10:00:00',date_sub(curdate() , interval 1 day),'11:00:00');
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (6,301,date_sub(curdate() , interval 1 day),'10:00:00',date_sub(curdate() , interval 1 day),'11:00:00');
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (7,302,date_sub(curdate() , interval 1 day),'10:00:00',date_sub(curdate() , interval 1 day),'11:00:00');
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (8,401,date_sub(curdate() , interval 1 day),'10:00:00',date_sub(curdate() , interval 1 day),'11:00:00');


# Estacionamientos abiertos

# Mitre al 100
# La tarjeta 1 (auto con patente AAA111) acaba de ingresar en el parquimetro de Mitre 101
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (1,101,CURDATE(),CURTIME(),NULL,NULL);

# Mitre al 200
#La tarjeta 2 (auto con patente AAA222) acaba de ingresar en el parquimetro de Mitre  201
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (2,201,CURDATE(),CURTIME(),NULL,NULL);

# Alem al 300
#La tarjeta 3 (auto con patente AAA333) acaba de ingresar en el parquimetro de Alem al 301
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (3,301,CURDATE(),CURTIME(),NULL,NULL);

#La tarjeta 7 (auto con patente AAA777) acaba de ingresar en el parquimetro de Alem al 301
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (7,301,CURDATE(),ADDTIME(CURTIME(), '00:00:01'),NULL,NULL);


#La tarjeta 4 (auto con patente AAA444) acaba de ingresar en el parquimetro de Alem al 302
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (4,302,CURDATE(),CURTIME(),NULL,NULL);


# Alem al 400
#La tarjeta 5 (auto con patente AAA555) acaba de ingresar en el parquimetro de Alem al 401
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (5,401,CURDATE(),CURTIME(),NULL,NULL);

#La tarjeta 12 (auto con patente AAA112) ingreso en el parquimetro de alem  401 ayer (se lo olvido abierto)
INSERT INTO Estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)
VALUES (12,401,date_sub(curdate() , interval 1 day),'11:0:00',NULL,NULL);


#-------------------------Asociado_con----------------------------------#
# ubicación: Mitre al 200
# inspector 10 asociado todos los dias y turnos (comodin)

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1011,10,'Alem',300,'Lu','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1012,10,'Alem',300,'Lu','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1021,10,'Alem',300,'Ma','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1022,10,'Alem',300,'Ma','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1031,10,'Alem',300,'Mi','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1032,10,'Alem',300,'Mi','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1041,10,'Alem',300,'Ju','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1042,10,'Alem',300,'Ju','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1051,10,'Alem',300,'Vi','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1052,10,'Alem',300,'Vi','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1061,10,'Alem',300,'Sa','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1062,10,'Alem',300,'Sa','T');

INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1071,10,'Alem',300,'Do','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (1072,10,'Alem',300,'Do','T');



# ubicación: Mitre al 100:
# inspector 1 asociado los lunes mañana y tarde
# inspector 2 asociado los martes mañana y tarde
# ...
# inspector 7 asociado los domingos mañana y tarde

#Inspector 1
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (11,1,'Mitre',100,'Lu','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (12,1,'Mitre',100,'Lu','T');
#Inspector 2
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (21,2,'Mitre',100,'Ma','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (22,2,'Mitre',100,'Ma','T');
#Inspector 3
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (31,3,'Mitre',100,'Mi','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (32,3,'Mitre',100,'Mi','T');
#Inspector 4
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (41,4,'Mitre',100,'Ju','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (42,4,'Mitre',100,'Ju','T');
#Inspector 5
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (51,5,'Mitre',100,'Vi','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (52,5,'Mitre',100,'Vi','T');
#Inspector 6
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (61,6,'Mitre',100,'Sa','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (62,6,'Mitre',100,'Sa','T');
#Inspector 7
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (71,7,'Mitre',100,'Do','M');
INSERT INTO Asociado_con(id_asociado_con,legajo,calle,altura,dia,turno)
VALUES (72,7,'Mitre',100,'Do','T');


#-------------------------Multa---------------------------------#


INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA111',11);
INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA111',31);
INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA111',51);
INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA111',71);


INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA222',21);
INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA222',41);
INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA222',61);


INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA333',31);

INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA444',41);

INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA555',51);

INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA666',61);

INSERT INTO Multa(fecha,hora,patente,id_asociado_con)
VALUES (curdate()-1,'18:10:35','AAA777',71);

#--------------------------------------------------------------------#
