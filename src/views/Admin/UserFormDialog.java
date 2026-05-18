package views.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import controller.PreguntaController;
import models.User;
import utilidades.ValidadorCadena;
import utilidades.views.PanelTipoPreguntaUtil;

public class UserFormDialog extends JDialog {
    private JPanel panelContenedorCentral;
    private User usuario;
    private boolean saved = false;
    
    // Parte 1: Datos personales
    private PanelTipoPreguntaUtil nombre, fechaNacimiento, curp, telefono, correo;
    
    // Parte 2: Datos laborales (CORREGIDO: Se eliminaron perfilPuesto, condicionesLaborales y ubicacionOrganizacional)
    private PanelTipoPreguntaUtil rol, descripcionFunciones, tipoContrato;
    
    // Parte 3: Datos médicos y bancarios
    private PanelTipoPreguntaUtil nss, alergiasConocidas, contactoEmergencia, banco, numeroCuenta, sueldo;

    // Lista global de todas las preguntas de tipo PanelTipoPreguntaUtil
    private List<PanelTipoPreguntaUtil> listaPreguntas;

    // Selectores y botones
    private JComboBox<String> estadoCivil, generos, tipoSangre;
    private ButtonGroup radioTurno;
    private JButton botonCancelar, botonGuardar;

    public UserFormDialog(JFrame parent, User usuario) {
        super(parent, true);
        this.usuario = usuario;
        
        setSize(400, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(true);
        setTitle("Formulario de Usuario");
        setLocationRelativeTo(null);
        
        inicializarComponentes();
        setVisible(true);
    }
    
    private void inicializarComponentes() {
        listaPreguntas = new ArrayList<>(); 

        JPanel panelSuperior = new JPanel();
        panelContenedorCentral = new JPanel(new BorderLayout());
        JPanel panelInferior = new JPanel();

        // Configuración de Título
        JLabel lblTitulo = new JLabel(usuario == null ? "Agregar usuario" : "Editar usuario");
        lblTitulo.setFont(new Font("Times", Font.PLAIN, 17));
        panelSuperior.add(lblTitulo);
        
        // Botón cancelar
        botonCancelar = new JButton("Cancelar");
        botonCancelar.setBackground(new Color(255, 25, 45));
        panelInferior.add(botonCancelar);
        
        // Botón Guardar
        botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(new Color(144, 224, 239));
        panelInferior.add(botonGuardar);

        // Crear y añadir el formulario (con scroll)
        panelContenedorCentral.add(crearFormularioCompleto(), BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenedorCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        loadData();
        
        botonGuardar.addActionListener(e -> save());
        botonCancelar.addActionListener(e -> dispose());
    }

    private JScrollPane crearFormularioCompleto() {
        JPanel panelCuestionario = new JPanel();
        panelCuestionario.setLayout(new BoxLayout(panelCuestionario, BoxLayout.Y_AXIS));
        panelCuestionario.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Parte 1
        nombre = new PanelTipoPreguntaUtil("Nombre", "ALFABETICO");
        fechaNacimiento = new PanelTipoPreguntaUtil("Fecha de nacimiento", "FECHA");
        curp = new PanelTipoPreguntaUtil("Curp", "ALFANUMERICO");
        telefono = new PanelTipoPreguntaUtil("Telefono", "NUMERICO");
        correo = new PanelTipoPreguntaUtil("Correo", "CORREO");

        // Parte 2 (CORREGIDO)
        rol = new PanelTipoPreguntaUtil("Puesto actual:", "ALFANUMERICO");
        descripcionFunciones = new PanelTipoPreguntaUtil("Funciones", "ALFANUMERICO");
        tipoContrato = new PanelTipoPreguntaUtil("Tipo contrato:", "ALFANUMERICO");
        
        // Parte 3
        nss = new PanelTipoPreguntaUtil("NSS:", "ALFANUMERICO");
        alergiasConocidas = new PanelTipoPreguntaUtil("Alergias:", "ALFANUMERICO");
        contactoEmergencia = new PanelTipoPreguntaUtil("Contacto Emergencia:", "ALFANUMERICO");
        banco = new PanelTipoPreguntaUtil("Banco:", "ALFANUMERICO");
        numeroCuenta = new PanelTipoPreguntaUtil("Cuenta/CLABE:", "NUMERICO");
        sueldo = new PanelTipoPreguntaUtil("Sueldo:", "NUMERICO");

        // Añadir campos válidos a la lista 
        Collections.addAll(listaPreguntas, 
            nombre, fechaNacimiento, curp, telefono, correo,
            rol, descripcionFunciones, tipoContrato,
            nss, alergiasConocidas, contactoEmergencia, banco, numeroCuenta, sueldo
        );

        // Añadir todos los paneles dinámicos al contenedor visual
        for (PanelTipoPreguntaUtil p : listaPreguntas) {
            panelCuestionario.add(p);
        }
        
        conectarPreguntasAsuControlador(listaPreguntas);
        
        // Tipo de Sangre
        JLabel lblSangre = new JLabel("Tipo de sangre");
        panelCuestionario.add(lblSangre);
        String[] opcionesSangre = {"Seleccionar", "O-", "O+", "B-", "B+", "A-", "A+", "AB+", "AB-"};
        tipoSangre = new JComboBox<>(opcionesSangre);
        tipoSangre.setSelectedIndex(0);
        panelCuestionario.add(tipoSangre);
        tipoSangre.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Turno
        JLabel lblTurno = new JLabel("Turno");
        panelCuestionario.add(lblTurno);
        radioTurno = new ButtonGroup();
        
        JRadioButton rbMatutino = new JRadioButton("Matutino"); 
        rbMatutino.setActionCommand("Matutino");
        panelCuestionario.add(rbMatutino);
        
        JRadioButton rbVespertino = new JRadioButton("Vespertino"); 
        rbVespertino.setActionCommand("Vespertino");
        panelCuestionario.add(rbVespertino);
        
        JRadioButton rbMixto = new JRadioButton("Mixto"); 
        rbMixto.setActionCommand("Mixto");
        panelCuestionario.add(rbMixto);
        
        radioTurno.add(rbMatutino);
        radioTurno.add(rbVespertino);
        radioTurno.add(rbMixto);
       
        // Estado civil
        JLabel lblEstadoCivil = new JLabel("Estado Civil ");
        panelCuestionario.add(lblEstadoCivil);
        String[] opcionesEstadoCivil = {"Seleccionar", "Soltero", "Casado", "Union libre", "Viudo"};
        estadoCivil = new JComboBox<>(opcionesEstadoCivil);
        estadoCivil.setSelectedIndex(0);
        panelCuestionario.add(estadoCivil);
        
        // Género
        JLabel lblGenero = new JLabel("Género ");
        panelCuestionario.add(lblGenero);
        String[] opcionesGenero = {"Seleccionar", "Hombre", "Mujer", "Therian", "Otro"};
        generos = new JComboBox<>(opcionesGenero);
        generos.setSelectedIndex(0);
        panelCuestionario.add(generos);

        return new JScrollPane(panelCuestionario);
    }
    
    public void conectarPreguntasAsuControlador(List<PanelTipoPreguntaUtil> preguntas) {
        for (PanelTipoPreguntaUtil p : preguntas) {
            PreguntaController.registrarPanel(p);
        }
    }
    
    private void loadData() {
        if (usuario != null) {
            // --- Parte 1: Datos Personales ---
            nombre.getTxtEntrada().setText(usuario.getNombre());
            fechaNacimiento.getTxtEntrada().setText(usuario.getFechaNacimiento());
            curp.getTxtEntrada().setText(usuario.getCurp());
            telefono.getTxtEntrada().setText(usuario.getTelefono());
            correo.getTxtEntrada().setText(usuario.getCorreo());
            
            generos.setSelectedItem(usuario.getGenero());
            estadoCivil.setSelectedItem(usuario.getEstadoCivil());

            // --- Parte 2: Datos Laborales (CORREGIDO) ---
            rol.getTxtEntrada().setText(usuario.getRol());
            descripcionFunciones.getTxtEntrada().setText(usuario.getDescripcionFunciones());
            tipoContrato.getTxtEntrada().setText(usuario.getTipoContrato());

            if (usuario.getTurno() != null) {
                String turno = usuario.getTurno();
                Enumeration<AbstractButton> buttons = radioTurno.getElements();
                while (buttons.hasMoreElements()) {
                    JRadioButton button = (JRadioButton) buttons.nextElement();
                    if (button.getText().equals(turno)) {
                        button.setSelected(true);
                        break;
                    }
                }
            }

            // --- Parte 3: Datos Médicos y Bancarios ---
            nss.getTxtEntrada().setText(usuario.getNSS());
            alergiasConocidas.getTxtEntrada().setText(usuario.getAlergiasConocidas());
            contactoEmergencia.getTxtEntrada().setText(usuario.getContactoEmergencia());
            banco.getTxtEntrada().setText(usuario.getBanco());
            numeroCuenta.getTxtEntrada().setText(usuario.getNumeroCuenta());
            sueldo.getTxtEntrada().setText(usuario.getSueldo());
            
            tipoSangre.setSelectedItem(usuario.getTipoDeSangre());
        }
    }
    
    private void save() {
        if (!validarFormulario()) {
            return;
        }
        
        // Obtener el comando del turno de forma segura por si no hay selección
        String turnoSeleccionado = (radioTurno.getSelection() != null) ? radioTurno.getSelection().getActionCommand() : "";
        
        if (usuario == null) {
            usuario = new User(
                nombre.obtenerTextoEntrada(),
                fechaNacimiento.obtenerTextoEntrada(),
                curp.obtenerTextoEntrada(),
                telefono.obtenerTextoEntrada(),
                correo.obtenerTextoEntrada(),
                (String) estadoCivil.getSelectedItem(),
                (String) generos.getSelectedItem(),
                rol.obtenerTextoEntrada(),
                descripcionFunciones.obtenerTextoEntrada(),
                tipoContrato.obtenerTextoEntrada(),
                turnoSeleccionado,
                nss.obtenerTextoEntrada(),
                alergiasConocidas.obtenerTextoEntrada(),
                contactoEmergencia.obtenerTextoEntrada(),
                (String) tipoSangre.getSelectedItem(),
                banco.obtenerTextoEntrada(),
                numeroCuenta.obtenerTextoEntrada(),
                sueldo.obtenerTextoEntrada()
            );
        } else {
            usuario.setNombre(nombre.obtenerTextoEntrada());
            usuario.setFechaNacimiento(fechaNacimiento.obtenerTextoEntrada());
            usuario.setCurp(curp.obtenerTextoEntrada());
            usuario.setTelefono(telefono.obtenerTextoEntrada());
            usuario.setCorreo(correo.obtenerTextoEntrada());
            usuario.setEstadoCivil((String) estadoCivil.getSelectedItem());
            usuario.setGenero((String) generos.getSelectedItem());
            
            usuario.setRol(rol.obtenerTextoEntrada());
            usuario.setDescripcionFunciones(descripcionFunciones.obtenerTextoEntrada());
            usuario.setTipoContrato(tipoContrato.obtenerTextoEntrada());
            usuario.setTurno(turnoSeleccionado);
            
            usuario.setNSS(nss.obtenerTextoEntrada());
            usuario.setAlergiasConocidas(alergiasConocidas.obtenerTextoEntrada());
            usuario.setContactoEmergencia(contactoEmergencia.obtenerTextoEntrada());
            usuario.setTipoDeSangre((String) tipoSangre.getSelectedItem());
            usuario.setBanco(banco.obtenerTextoEntrada());
            usuario.setNumeroCuenta(numeroCuenta.obtenerTextoEntrada());
            usuario.setSueldo(sueldo.obtenerTextoEntrada());
        }
        
        saved = true;
        dispose();
    }
            
    public boolean isSaved() {
        return saved;
    }
    
    public User getUsuario() {
        return usuario;
    }

    private boolean validarFormulario() {
        boolean formularioListo = true; 
        
        for (PanelTipoPreguntaUtil pregunta : listaPreguntas) {
            if (pregunta.estaVacio()) {
                pregunta.senalarEntradaVacia();
                formularioListo = false;
            }
        }
        
        // Comprobar comboboxes obligatorios
        if ("Seleccionar".equals(estadoCivil.getSelectedItem()) || "Seleccionar".equals(generos.getSelectedItem())) {
            formularioListo = false;
        }
        
        // Comprobar JRadioButtons del turno
        if (radioTurno.getSelection() == null) {
            formularioListo = false;
        }
        
        // Comprobar combobox de tipo de sangre
        if (tipoSangre.getSelectedIndex() == 0) {
            formularioListo = false;
        }
                
        // Validar tipos de contenidos usando tu validador de cadenas
        for (PanelTipoPreguntaUtil pregunta : listaPreguntas) {
            try {
                ValidadorCadena.validarContenido(pregunta);
            } catch (Exception e) {
                formularioListo = false;
            }    
        }
        
        return formularioListo;
    }
}