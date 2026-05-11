package views;

import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import tablemodels.UserTableModel;
import utilidades.Config;

public class UsersView extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnExportPDF; 
    private JLabel advertencias;

    public UsersView() {
        setTitle("Administración de Empleados - Madero's");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(30, 30, 30)); 
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panelAcciones = new JPanel();
        panelAcciones.setBackground(new Color(45, 45, 45));
        contentPane.add(panelAcciones, BorderLayout.NORTH);

        btnAdd = new JButton("Añadir");
        btnEdit = new JButton("Editar");
        btnDelete = new JButton("Eliminar");
        btnExportPDF = new JButton("Exportar PDF"); 

        configurarBoton(btnAdd, new Color(0, 153, 51)); 
        configurarBoton(btnEdit, Color.YELLOW); 
        btnEdit.setForeground(Color.BLACK); 
        configurarBoton(btnDelete, new Color(204, 0, 0));
        configurarBoton(btnExportPDF, new Color(255, 102, 0)); 
        
        panelAcciones.add(btnAdd);
        panelAcciones.add(btnEdit);
        panelAcciones.add(btnDelete);
        panelAcciones.add(btnExportPDF);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        advertencias = new JLabel("Listo");
        advertencias.setForeground(Color.WHITE);
        contentPane.add(advertencias, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
   
    
    private void aplicarEstiloTabla() {
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 102, 0, 100));
        table.setRowHeight(35); 
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(230, 230, 230));

        JTableHeader encabezadoVista = table.getTableHeader();
        encabezadoVista.setBackground(new Color(40, 40, 40)); 
        encabezadoVista.setForeground(Color.WHITE);
        encabezadoVista.setFont(new Font("Segoe UI", Font.BOLD, 14));
        encabezadoVista.setReorderingAllowed(false);
        encabezadoVista.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer alineacionCentrada = new DefaultTableCellRenderer();
        alineacionCentrada.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(alineacionCentrada);
        }
    }

    private void configurarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorderPainted(false); 
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
    }

    public void setTableModel(UserTableModel model) {
        table.setModel(model);
        aplicarEstiloTabla(); 
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnExportPDF() { return btnExportPDF; }
    public JLabel getAdvertencias() { return advertencias; }
}