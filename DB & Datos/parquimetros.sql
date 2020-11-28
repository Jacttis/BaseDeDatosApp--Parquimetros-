#Archivo batch parquimetros.sql para la creacion de la base de datos
#Creo la base de datos
CREATE DATABASE parquimetros;

#selecciono la base da datos en la cual hago modificaciones

USE parquimetros;

#---------------------------------------------------------------------------

#Creacion Tablas para las entidades

CREATE TABLE conductores(
    nombre VARCHAR (45) NOT NULL,
    apellido VARCHAR (45) NOT NULL,
    direccion VARCHAR (45) NOT NULL,
    telefono VARCHAR (45) ,
    registro INT  unsigned NOT NULL,
    dni INT unsigned NOT NULL,

    CONSTRAINT pk_conductores
    PRIMARY KEY (dni)
)ENGINE=InnoDB;

CREATE TABLE automoviles(
    marca VARCHAR (45) NOT NULL,
    modelo VARCHAR (45) NOT NULL,
    color VARCHAR (45) NOT NULL,
    patente VARCHAR (6) NOT NULL,
    dni INT UNSIGNED NOT NULL ,

    CONSTRAINT pk_automoviles
    PRIMARY KEY (patente),

    CONSTRAINT fk_es_automoviles_conductores
        FOREIGN KEY (dni) REFERENCES conductores (dni)
            ON DELETE RESTRICT ON UPDATE CASCADE


)ENGINE=InnoDB;

CREATE TABLE tipos_tarjeta(
    tipo VARCHAR (45) NOT NULL,
    descuento DECIMAL (3,2)  UNSIGNED NOT NULL,

    CONSTRAINT chk_tipos_tarjeta_descuento CHECK (descuento<=1 and descuento>=0),
    CONSTRAINT pk_tipos_tarjeta
    PRIMARY KEY (tipo)

)ENGINE=InnoDB;

CREATE TABLE tarjetas(
    id_tarjeta INT unsigned NOT NULL AUTO_INCREMENT,
    saldo DECIMAL(5,2) NOT NULL,
    patente VARCHAR (6) NOT NULL,
    tipo VARCHAR(45) NOT NULL,

    CONSTRAINT pk_tarjetas
    PRIMARY KEY (id_tarjeta),

    CONSTRAINT fk_tarjetas_automoviles
        FOREIGN KEY (patente) REFERENCES automoviles (patente)
            ON DELETE RESTRICT ON UPDATE CASCADE ,

    CONSTRAINT fk_tarjetas_tipos_tarjeta
        FOREIGN KEY (tipo) REFERENCES tipos_tarjeta (tipo)
            ON DELETE RESTRICT ON UPDATE CASCADE



)ENGINE=InnoDB;





CREATE TABLE inspectores(
    legajo INT unsigned NOT NULL,
    dni INT unsigned NOT NULL,
    nombre VARCHAR(45) NOT NULL,
    apellido VARCHAR (45) NOT NULL,
    password VARCHAR (32) NOT NULL,

    CONSTRAINT pk_inspectores
    PRIMARY KEY (legajo)


)ENGINE=InnoDB;

CREATE TABLE ubicaciones(
    calle VARCHAR(45) NOT NULL,
    altura INT UNSIGNED NOT NULL,
    tarifa DECIMAL(5,2) UNSIGNED NOT NULL ,

    CONSTRAINT pk_ubicaciones
    PRIMARY KEY (calle,altura)

)ENGINE=InnoDB;

CREATE TABLE parquimetros(
    id_parq INT UNSIGNED NOT NULL ,
    numero INT UNSIGNED NOT NULL ,
    calle VARCHAR(45) NOT NULL,
    altura INT UNSIGNED NOT NULL,
    CONSTRAINT pk_parquimetros
    PRIMARY KEY (id_parq),

    CONSTRAINT fk_parquimetros_ubicaciones
        FOREIGN KEY (calle,altura) REFERENCES ubicaciones(calle,altura)
            ON DELETE RESTRICT ON UPDATE CASCADE


)ENGINE=InnoDB;

# Creación Tablas para las relaciones

CREATE TABLE estacionamientos(
    id_tarjeta INT UNSIGNED NOT NULL ,
    id_parq INT UNSIGNED NOT NULL ,
    fecha_ent DATE  ,
    hora_ent TIME ,
    fecha_sal DATE  ,
    hora_sal TIME ,

    CONSTRAINT pk_estacionamientos
    PRIMARY KEY (id_parq,fecha_ent,hora_ent),

    CONSTRAINT fk_estacionamientos_parquimetros
    FOREIGN KEY (id_parq) REFERENCES parquimetros (id_parq)
        ON DELETE RESTRICT ON UPDATE CASCADE ,

    CONSTRAINT fk_estacionamientos_tarjetas
    FOREIGN KEY (id_tarjeta) REFERENCES tarjetas (id_tarjeta)
        ON DELETE RESTRICT ON UPDATE CASCADE

)ENGINE=InnoDB;

CREATE TABLE accede(
    legajo INT UNSIGNED NOT NULL ,
    id_parq INT UNSIGNED NOT NULL ,
    fecha DATE NOT NULL ,
    hora TIME NOT NULL ,

    CONSTRAINT pk_accede
    PRIMARY KEY (id_parq,fecha,hora),

    CONSTRAINT fk_accede_parquimetros
        FOREIGN KEY (id_parq) REFERENCES parquimetros (id_parq)
            ON DELETE RESTRICT ON UPDATE CASCADE ,

    CONSTRAINT fk_accede_inspectores
        FOREIGN KEY (legajo) REFERENCES inspectores (legajo)
            ON DELETE RESTRICT ON UPDATE CASCADE


)ENGINE=InnoDB;

CREATE TABLE asociado_con(
    id_asociado_con INT UNSIGNED NOT NULL AUTO_INCREMENT ,
    legajo INT UNSIGNED NOT NULL ,
    calle VARCHAR(45) NOT NULL ,
    altura INT UNSIGNED NOT NULL ,
    dia ENUM('do','lu','ma','mi','ju','vi','sa') NOT NULL ,
    turno enum('m','t') NOT NULL,

    CONSTRAINT pk_asociado
    PRIMARY KEY (id_asociado_con),

    CONSTRAINT fk_asociado_con_inspectores
        FOREIGN KEY (legajo) REFERENCES inspectores (legajo)
            ON DELETE RESTRICT ON UPDATE CASCADE ,

    CONSTRAINT fk_asociado_con_ubicaciones_CALLE
        FOREIGN KEY (calle,altura) REFERENCES ubicaciones (calle,altura)
            ON DELETE RESTRICT ON UPDATE CASCADE



)ENGINE InnoDB;

CREATE TABLE multa(
    numero INT UNSIGNED NOT NULL AUTO_INCREMENT ,
    fecha DATE NOT NULL ,
    hora TIME NOT NULL ,
    patente VARCHAR(6)NOT NULL ,
    id_asociado_con INT UNSIGNED NOT NULL ,

    CONSTRAINT pk_multa
    PRIMARY KEY (numero),

    CONSTRAINT fk_multa_automoviles
        FOREIGN KEY (patente) REFERENCES automoviles (patente)
            ON DELETE RESTRICT ON UPDATE CASCADE ,

    CONSTRAINT fk_multa_asociado_con
        FOREIGN KEY (id_asociado_con) REFERENCES asociado_con (id_asociado_con)
            ON DELETE RESTRICT ON UPDATE CASCADE



)ENGINE =InnoDB;



#-------------------------------------------------------------------------
# Creación de usuarios y otorgamiento de privilegios


# Este usuario se utilizar´a para administrar la base de datos “parquimetros” por lo
# tanto deber´a tener acceso total sobre todas las tablas, con la opci´on de crear usuarios y
# otorgar privilegios sobre las mismas. Para no comprometer la seguridad se restringir´a que el
# acceso de este usuario se realice s´olo desde la m´aquina local donde se encuentra el servidor
# MySQL. El password de este usuario deber´a ser: admin.

CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

GRANT ALL PRIVILEGES ON parquimetros.* TO 'admin'@'localhost' WITH GRANT OPTION;

# Este usuario est´a destinado a permitir el acceso de la aplicaci´on de venta de tarjetas
# y deber´a tener privilegios m´ınimos para cargar una nueva tarjeta con un saldo inicial y de
# cualquier tipo. El password de este usuario deber´a ser: venta.

CREATE USER 'venta'@'%' IDENTIFIED BY 'venta';

GRANT INSERT ON parquimetros.tarjetas TO 'venta'@'%';
GRANT SELECT ON parquimetros.tarjetas TO 'venta'@'%';

GRANT SELECT ON parquimetros.tipos_tarjeta TO 'venta'@'%';

CREATE USER 'inspector'@'%' IDENTIFIED BY 'inspector';

CREATE VIEW estacionados AS
    SELECT calle , altura , patente
    FROM ((estacionamientos as e JOIN tarjetas as p ON e.id_tarjeta = p.id_tarjeta) JOIN parquimetros as l ON E.id_parq=l.id_parq)
    WHERE e.hora_ent IS NOT NULL and e.fecha_ent IS NOT NULL and e.fecha_sal IS NULL and e.hora_sal IS NULL;
GRANT SELECT ON parquimetros.inspectores TO 'inspector'@'%';
GRANT SELECT ON parquimetros.estacionados TO 'inspector'@'%';
GRANT INSERT ON parquimetros.multa TO 'inspector'@'%';
GRANT INSERT ON parquimetros.accede TO 'inspector'@'%';
GRANT SELECT ON parquimetros.multa TO 'inspector'@'%';
GRANT SELECT ON parquimetros.accede TO 'inspector'@'%';
GRANT SELECT ON parquimetros.parquimetros TO 'inspector'@'%';
GRANT SELECT ON parquimetros.asociado_con TO 'inspector'@'%';

/*Proyecto 3*/


#Tabla Ventas
CREATE TABLE ventas(
    id_tarjeta INT unsigned NOT NULL AUTO_INCREMENT,
    tipo VARCHAR(45),
    saldo DECIMAL(5,2) NOT NULL,
    fecha DATE,
    hora TIME,

    CONSTRAINT pk_ventas
        PRIMARY KEY (id_tarjeta),

    CONSTRAINT fk_ventas_tarjetas_id_tarjeta
        FOREIGN KEY(id_tarjeta) REFERENCES tarjetas(id_tarjeta)
            ON DELETE RESTRICT ON UPDATE CASCADE,

    CONSTRAINT fk_ventas_tarjetas_tipo
        FOREIGN KEY(tipo) REFERENCES tarjetas(tipo)
            ON DELETE RESTRICT ON UPDATE CASCADE


)ENGINE=InnoDB;

#trigger
delimiter !

CREATE TRIGGER traza_ventas
    AFTER INSERT ON tarjetas
    FOR EACH ROW
    BEGIN
        INSERT INTO ventas(id_tarjeta, tipo, saldo, fecha, hora) VALUE (NEW.id_tarjeta,NEW.tipo,NEW.saldo,curdate(),curtime());
    end; !
    delimiter ;

#Procedure conectar
DELIMITER !
create procedure conectar(IN id_tarjeta INTEGER,IN id_parq INTEGER)
begin
     DECLARE fechaEntrada , fechaSalida DATE;
    DECLARE horaEntrada, horaSalida TIME;
    DECLARE tarjeta ,parquimetro INTEGER;
    DECLARE saldoActual DECIMAL;
    DECLARE descuentoAplicado DECIMAL(3,2);
    DECLARE tiempoTranscurrido INTEGER;
    DECLARE tiempoRestante INTEGER;
    DECLARE tarifaActual DECIMAL(5,2);
    DECLARE abierto BOOL;
    DECLARE codigo_SQL CHAR(5) DEFAULT '00000';
    DECLARE codigo_MYSQL INT DEFAULT 0;
    DECLARE mensaje_error TEXT;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
            GET DIAGNOSTICS CONDITION 1 codigo_MYSQL= MYSQL_ERRNO,
            codigo_SQL= RETURNED_SQLSTATE,
            mensaje_error= MESSAGE_TEXT;
            SELECT 'SQLEXCEPTION, transacción abortada' AS resultado,
            codigo_MySQL, codigo_SQL, mensaje_error;
            ROLLBACK;

        end ;

    START TRANSACTION;

        if(id_tarjeta is not null and id_parq is not null ) then
            set tarjeta=id_tarjeta;
            set  parquimetro=id_parq;
            set abierto=false;
            SELECT saldo,descuento INTO saldoActual, descuentoAplicado FROM tarjetas t natural join tipos_tarjeta tt where tarjeta=t.id_tarjeta limit 1;
            SELECT tarifa INTO tarifaActual FROM parquimetros p NATURAL JOIN ubicaciones u WHERE p.id_parq=parquimetro limit 1;
            if(EXISTS(SELECT fecha_ent,hora_ent,fecha_sal,hora_sal FROM estacionamientos e WHERE tarjeta=e.id_tarjeta order by fecha_sal,hora_sal desc)) then
                SELECT fecha_ent,hora_ent,fecha_sal,hora_sal INTO fechaEntrada,horaEntrada,fechaSalida,horaSalida FROM estacionamientos e WHERE tarjeta=e.id_tarjeta order by fecha_sal,hora_sal desc limit 1;
                if(fechaSalida is NULL and  horaSalida is null) then
                    set abierto=true;
                    set fechaSalida=CURDATE();
                    set horaSalida=CURTIME();
                    set tiempoTranscurrido= TIMESTAMPDIFF(MINUTE,CONCAT(fechaEntrada, ' ', horaEntrada),CONCAT(fechaSalida, ' ', horaSalida));
                    set saldoActual=GREATEST(-999.99, TRUNCATE(saldoActual-(tiempoTranscurrido*tarifaActual*(1-descuentoAplicado)),2));
                    UPDATE estacionamientos e set e.fecha_sal=fechaSalida,e.hora_sal=horaSalida  where e.id_tarjeta=tarjeta and e.fecha_ent=fechaEntrada and e.hora_ent=horaEntrada;
                    UPDATE tarjetas t set t.saldo=saldoActual where t.id_tarjeta=tarjeta;
                    SELECT 'Cierre' as operacion, saldoActual as saldo ,tiempoTranscurrido;
                end if ;

            end if;

           if(not EXISTS(SELECT fecha_ent,hora_ent, fecha_sal, hora_sal FROM estacionamientos e WHERE tarjeta=e.id_tarjeta order by fecha_sal,hora_sal desc limit 1) or abierto=false) then
                if(saldoActual>0) then
                    set fechaEntrada=CURDATE();
                    set horaEntrada=CURTIME();
                    set tiempoRestante = saldoActual/(tarifaActual*(1-descuentoAplicado));
                    INSERT INTO estacionamientos(id_tarjeta,id_parq,fecha_ent,hora_ent,fecha_sal,hora_sal)VALUES (tarjeta,parquimetro,fechaEntrada,horaEntrada,null,null);
                    SELECT 'Apertura' as operacion, 'La operacion se realizo con exito' as resultado ,tiempoRestante;
                else
                    begin
                        SELECT 'Saldo de la tarjeta insuficiente' as resultado;
                    end;
                end if;

            end if ;

        else
            begin
                SELECT 'Error tarjeta o parquimetro inexistente' as resultado;
            end;
        end if ;
COMMIT;
end !

DELIMITER ;

#Usuario parquimetros
CREATE USER 'parquimetro'@'%' IDENTIFIED  BY 'parq';
GRANT EXECUTE ON PROCEDURE parquimetros.conectar to 'parquimetro'@'%';
GRANT SELECT on parquimetros.ubicaciones TO 'parquimetro'@'%';
GRANT SELECT on parquimetros.parquimetros TO 'parquimetro'@'%';
GRANT SELECT on parquimetros.tarjetas TO 'parquimetro'@'%';










