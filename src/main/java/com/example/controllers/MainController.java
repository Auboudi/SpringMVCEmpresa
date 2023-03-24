package com.example.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    
}
