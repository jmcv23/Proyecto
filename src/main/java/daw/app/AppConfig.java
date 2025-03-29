package daw.app;

import daw.app.modelo.Dao.ReservaDao;
import daw.app.modelo.Dao.UsuarioDao;
import daw.app.modelo.Dao.ValoracionDao;
import daw.app.modelo.Dao.VehiculoDao;
import daw.app.modelo.Reserva;
import daw.app.modelo.Usuario;
import daw.app.modelo.Valoracion;
import daw.app.modelo.Vehiculo;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.Startup;
import jakarta.enterprise.inject.Default;
import jakarta.faces.annotation.FacesConfig;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Logger;

import static java.util.logging.Logger.*;

@FacesConfig //enable JSF front-controller
@Named("app")
@Default
@ApplicationScoped
public class AppConfig {

    @Inject
    private UsuarioDao usuarioDao;


    @Inject
    private VehiculoDao vehiculoDao;

    @Inject
    private ReservaDao reservaDao;

    @Inject
    private ValoracionDao valoracionDao;

    private final Logger log= getLogger(AppConfig.class.getName());

    private final String message = "Welcome DAW!";

    public AppConfig() {
        log.info(">>> Application starting...");
    }

    /** Automatically called when all dependencies are satisfied */
    public void onStartup(@Observes Startup event) {
        log.info(">>> Application ready");
        createSampleData();
    }

    public String getWelcomeMessage() {
        return message;
    }

    public void createSampleData() {
        log.info("Creando clientes de prueba");
        //set default ciphered password to sample customers: secreto
        usuarioDao.crear( new Usuario("Manuel Diaz Sanchez", LocalDate.parse("2000-08-17"), "45678901D", "manuel.sanchez@email.com", "password123", "Avenida Madrid 202", "/resources/img/usu1.jpeg"));
        usuarioDao.crear( new Usuario("Carmen Ruiz Vega", LocalDate.parse("2005-08-17"), "12345678A", "carmen.ruiz@email.com", "password123", "Avenida Barcelona 5", "/resources/img/usu2.jpg"));
        usuarioDao.crear( new Usuario("Roberto Araque Lopez", LocalDate.parse("1990-03-17"), "87654321B", "roberto@email.com", "password123", "Calle Mirador 10", "/resources/img/usu3.jpeg"));

        valoracionDao.crear( new Valoracion("5678DFF","45678901D",5,"muy buen servicio"));
        valoracionDao.crear( new Valoracion("9101GHH","12345678A",1,"Pesimo servicio"));
        valoracionDao.crear( new Valoracion("5678DFF","87654321B",3,"Podria mejorarse"));


        vehiculoDao.crear(new Vehiculo("1234BBC", "BMW", "Corolla", "Coupé", "Automático", "Gasolina", 5, "Madrid",220, "/resources/img/bmwcoche.jpg"
        ));
        vehiculoDao.crear(new Vehiculo("5678DFF", "Range", "evoque", "SUV", "Automático", "Gasolina", 5, "Barcelona", 230, "/resources/img/landrovercoche.jpg"));
        vehiculoDao.crear(new Vehiculo("9101GHH", "Porsche", "gt3", "Coupé", "Automático", "Híbrido", 2, "Sevilla", 500, "/resources/img/porschecoche.jpg"));

        reservaDao.crear(new Reserva(
                1,
                Reserva.EstadoReserva.RESERVADO,
                "12345678A",
                "1234BBC",
                LocalDate.of(2025, 4, 25),
                Reserva.Ciudad.MADRID,
                LocalTime.of(10, 0),
                LocalDate.of(2025, 6, 30),
                Reserva.Ciudad.BARCELONA,
                LocalTime.of(18, 0),
                150.0f
        ));

        reservaDao.crear(new Reserva(
                14,
                Reserva.EstadoReserva.COMPLETADO,
                "87654321B",
                "5678DFF",
                LocalDate.of(2023, 11, 15),
                Reserva.Ciudad.BARCELONA,
                LocalTime.of(9, 30),
                LocalDate.of(2023, 11, 20),
                Reserva.Ciudad.VALENCIA,
                LocalTime.of(17, 45),
                200.0f
        ));

        reservaDao.crear( new Reserva(
                3,
                Reserva.EstadoReserva.CANCELADO,
                "98765432C",
                "9101GHH",
                LocalDate.of(2023, 10, 10),
                Reserva.Ciudad.BILBAO,
                LocalTime.of(14, 0),
                LocalDate.of(2023, 10, 15),
                Reserva.Ciudad.MADRID,
                LocalTime.of(12, 0),
                180.0f
        ));


    }
}
