package utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import models.User;

public class PDFExporter {

    public void exportUsers(JFrame ventanaPadre, List<User> listaUsuarios) {
        JFileChooser selectorDeArchivos = new JFileChooser();
        selectorDeArchivos.setDialogTitle("Guardar Reporte de Empleados");
        selectorDeArchivos.setSelectedFile(new File("Reporte_Empleados_Maderos.pdf"));

        int seleccion = selectorDeArchivos.showSaveDialog(ventanaPadre);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivoDestino = selectorDeArchivos.getSelectedFile();

            if (!archivoDestino.getName().toLowerCase().endsWith(".pdf")) {
                archivoDestino = new File(archivoDestino.getAbsolutePath() + ".pdf");
            }

            Document documentoPdf = new Document(PageSize.A4.rotate());

            try {
                PdfWriter.getInstance(documentoPdf, new FileOutputStream(archivoDestino));
                documentoPdf.open();

                // Fuentes
                Font fuenteTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
                Font fuenteCabecera = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

                Paragraph titulo = new Paragraph("RESTAURANTE MADERO'S - REPORTE DE PERSONAL", fuenteTitulo);
                titulo.setAlignment(Element.ALIGN_CENTER);
                documentoPdf.add(titulo);
                documentoPdf.add(new Paragraph(" ")); 

                PdfPTable tablaDatos = new PdfPTable(5);
                tablaDatos.setWidthPercentage(100);

                String[] encabezados = {"Nombre", "Puesto", "Teléfono", "Correo", "Sueldo"};
                for (String textoCabecera : encabezados) {
                    PdfPCell celdaCabecera = new PdfPCell(new Phrase(textoCabecera, fuenteCabecera));
                    celdaCabecera.setBackgroundColor(new BaseColor(255, 102, 0)); // Naranja Madero's
                    celdaCabecera.setHorizontalAlignment(Element.ALIGN_CENTER);
                    celdaCabecera.setPadding(8);
                    tablaDatos.addCell(celdaCabecera);
                }

                for (User usuario : listaUsuarios) {
                    tablaDatos.addCell(usuario.getNombre() != null ? usuario.getNombre() : "");
                    tablaDatos.addCell(usuario.getPuestoActual() != null ? usuario.getPuestoActual() : "");
                    tablaDatos.addCell(usuario.getTelefono() != null ? usuario.getTelefono() : "");
                    tablaDatos.addCell(usuario.getCorreo() != null ? usuario.getCorreo() : "");
                    tablaDatos.addCell("$" + (usuario.getSueldo() != null ? usuario.getSueldo() : "0"));
                }

                documentoPdf.add(tablaDatos);

                JOptionPane.showMessageDialog(ventanaPadre, "Reporte generado");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(ventanaPadre, "Error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (documentoPdf.isOpen()) {
                    documentoPdf.close();
                }
            }
        }
    }
}