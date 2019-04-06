package es.com.icontaweb.contratos.utilidades;

import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.mysql.jdbc.Connection;

public class GenerarJustificante {

    JasperReport jasperReport;
    JasperPrint jasperPrint;

    public GenerarJustificante() {

    }

    public void GeneraInforme(Integer numero, Object imagen) {

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = (String) servletContext.getRealPath("/");

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("logotipo", realPath + "/recursos/instelec.png");
            parametros.put("firma", realPath + "/recursos/imgfirma.png");
            parametros.put("bidiurl", realPath + "/informes/bidi-url.jpg");
            parametros.put("justificante", numero);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/contratos", "usuario", "icontaweb");

            JasperReport jasperReport = JasperCompileManager.compileReport(realPath + "/informes/justificante.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(jasperPrint, realPath + "/informes/justificante.pdf");

            System.out.println("icontaweb : " + numero.toString() + (new Date()).toString() + " - Generado PDF Justificante" + realPath);

        } catch (Exception e) {
            System.out.println("icontaweb : " + (new Date()).toString() + " Error PDF Justificante " + e.getMessage());
        }

    }

    public void GeneraMantenimiento(Integer numero, Object imagen) {

        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = (String) servletContext.getRealPath("/");

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("logotipo", realPath + "/recursos/instelec.png");
            parametros.put("firma", realPath + "/recursos/imgfirma.png");
            parametros.put("bidiurl", realPath + "/informes/bidi-url.jpg");
            parametros.put("justificante", numero);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/contratos", "usuario", "icontaweb");

            JasperReport jasperReport = JasperCompileManager.compileReport(realPath + "/informes/mantenimiento.jrxml");
            jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
            JasperExportManager.exportReportToPdfFile(jasperPrint, realPath + "/informes/mantenimiento.pdf");

            System.out.println("icontaweb : " + numero.toString() + (new Date()).toString() + " - Generado PDF Mnatenimiento" + realPath);

        } catch (Exception e) {
            System.out.println("icontaweb : " + (new Date()).toString() + " Error PDF Mantenimeinto " + e.getMessage());
        }

    }

}
