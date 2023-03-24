package com.example.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;
import com.example.services.TelefonoService;



@Controller
@RequestMapping("/")
public class MainController {
    
    private static final Logger LOG = Logger.getLogger("MainController");

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private TelefonoService telefonoService;

    @Autowired
    private CorreoService correoService;

    /**
     * Creamos un metodo que devuelve un listado de empleados
     */

     @GetMapping("/listar")
     public ModelAndView listar() {

        List<Empleado> empleados = empleadoService.findAll();

        ModelAndView mav = new ModelAndView("views/listarEmpleados");
        mav.addObject("empleados", empleados);

        return mav;

     }

     /**
      * Mostrar un formulario de alta de Empleado
      */

      @GetMapping("/frmAltaEmpleado")
      public String formularioAltaEmpleado(Model model) {

        List<Departamento> departamentos = departamentoService.findAll();

        Empleado empleado = new Empleado();

        model.addAttribute("empleado", empleado);
        model.addAttribute("departamentos", departamentos);

        return "views/formularioAltaEmpleado";


      }

      // Metodo que recupere los datos del Empleado modificado

      @PostMapping("/altaModificacionEmpleado")
      public String altaEmpleado(@ModelAttribute Empleado empleado,
                    @RequestParam(name = "numerosTelefonos") String telefonosRecibidos, 
                    @RequestParam(name = "emailsCorreos") String emailsRecibidos) {

                LOG.info("emails recibidos: " + emailsRecibidos);
                LOG.info("telefonos recibidos: " + telefonosRecibidos);

                empleadoService.save(empleado);

                List<String> listadoNumerosTelefonos = null;
                List<String> listadoEmailsCorreos = null;

                if(telefonosRecibidos != null){
                    String[] arrayTelefonos = telefonosRecibidos.split(";");
                    listadoNumerosTelefonos = Arrays.asList(arrayTelefonos);

                }
                if(listadoNumerosTelefonos != null){
                    telefonoService.deleteByEmpleado(empleado);
                    listadoNumerosTelefonos.stream().forEach(n -> {
                        Telefono telefonoObject = Telefono
                        .builder()
                        .numero(n)
                        .empleado(empleado)
                        .build();

                        telefonoService.save(telefonoObject);
                    });
                }

                if(emailsRecibidos != null) {
                    String[] arrayCorreos = emailsRecibidos.split(";");
                    listadoEmailsCorreos = Arrays.asList(arrayCorreos);
                }
                if(listadoEmailsCorreos != null){
                    correoService.deleteByEmpleado(empleado);
                    listadoEmailsCorreos.stream().forEach(e -> {
                        Correo correoObject = Correo
                        .builder()
                        .email(e)
                        .empleado(empleado)
                        .build();

                        correoService.save(correoObject);

                    });
                }

                return "redirect:/listar";
                    }

               /*
         * Metodo para Actualizar un empleado
         */
        @GetMapping("/frmActualizar/{id}")
        public String frmActualizarEmpleado(@PathVariable(name = "id") int idEmpleado, Model model) {

            Empleado empleado = empleadoService.findById(idEmpleado);
            List<Telefono> todosTelefonos = telefonoService.findAll();
            List<Telefono> telefonosDelEmpleado = todosTelefonos.stream()
                .filter(telefono -> telefono.getEmpleado().getId() == idEmpleado)
                .collect(Collectors.toList());

            String numerosDeTelefono = telefonosDelEmpleado.stream()
                .map(telefono -> telefono.getNumero())
                .collect(Collectors.joining(";"));
            
            List<Correo> todosCorreos = correoService.findAll();
            List<Correo> correosEmpleado = todosCorreos.stream()
                .filter(correo -> correo.getEmpleado().getId() == idEmpleado)
                .collect(Collectors.toList());
            String emailsCorreos = correosEmpleado.stream()
                .map(correo -> correo.getEmail())
                .collect(Collectors.joining(";"));

            List<Departamento> departamentos = departamentoService.findAll();


            model.addAttribute("departamentos", departamentos);
            model.addAttribute("telefonos", numerosDeTelefono);
            model.addAttribute("empleado", empleado);
            model.addAttribute("correos", emailsCorreos);


            return "views/formularioAltaEmpleado";
        }

        @GetMapping("/borrar/{id}")
        public String borrarEmpleado(@PathVariable(name = "id") int idEmpleado) {
            empleadoService.delete(empleadoService.findById(idEmpleado));

            return "redirect:/listar";
        }

        @GetMapping("/detalles/{id}")
        public String empleadoDetails(@PathVariable(name = "id") int id, Model model) {

            Empleado empleado = empleadoService.findById(id);
            List<Telefono> telefonos = telefonoService.findByEmpleado(empleado);
            List<Correo> correos = correoService.findByEmpleado(empleado);

            List<String> numerosTelefono = telefonos.stream()
                .map(t -> t.getNumero())
                .toList();
            List<String> emailsCorreo = correos.stream()
                .map(c -> c.getEmail())
                .toList();

                model.addAttribute("empleado", empleado);
                model.addAttribute("telefonos", numerosTelefono);
                model.addAttribute("correos", emailsCorreo);

            return "views/empleadoDetalles";
        }

}
