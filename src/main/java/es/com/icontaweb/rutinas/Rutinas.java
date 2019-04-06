/*
 * rutinas.java
 * 
 * Created on 02-jul-2007, 23:58:17
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package es.com.icontaweb.rutinas;

/**
 *
 * @author JCARLOS
 */
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Rutinas {

    /**
     * Devuelve 0
     * @return 
     */
    public static Double CERO() {
        return new Double(0);
    }

    /**
     * Devuelve al valor en una variable Double
     * @param S0 
     * @return 
     */
    public static Double VALOR(Object S0) {
	
	Double resultado = new Double(0);
            String SS = S0.toString();
	String S3 = S0.toString(); 

	try {
		if (SS.indexOf(",")>0) {
			String S1 = SS.replace(',','*');
			String S2 = S1.replace('.',',');
			S3 = S2.replace('*','.');
		}
                    if (S3.equals("")) { S3="0"; }
		resultado = Double.valueOf(S3);
	} catch (Exception e) {
		System.out.println(new Date().toString() + " - Error en Calculo valor : " + e.getMessage());
		resultado = new Double(0);
	}
	
	return resultado;
	
    }
    
    /**
     * Convierta un objeto en un string
     * @param ENTRADA
     * @return
     */
    public static String STRIN(Object ENTRADA) {
        
        String resultado = "";
        if (ENTRADA != null) { resultado = ENTRADA.toString(); }
        return resultado;
        
    }

    /**
     * Formatea un objeto ENTRADA según el FORMATO especificado
     * @param ENTRADA - El objeto a formatear
     * @param FORMATO - El formato de salida
     * @return
     */
	public static String FORMATO_NUMERO(Object ENTRADA, String FORMATO) {
		
		DecimalFormat fn = new DecimalFormat(FORMATO);
		return fn.format(Double.valueOf(ENTRADA.toString()));
		
	}
	
	/**
	 * Formatea un objeto ENTRADA en un FORMATO de fecha
	 * @param ENTRADA
	 * @param FORMATO
	 * @return
	 */
	public static String FORMATO_FECHA(Object ENTRADA, String FORMATO) {
		
            // SimpleDateFormat fn = new SimpleDateFormat ("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat fn = new SimpleDateFormat (FORMATO, Locale.getDefault());
            String FFECHA;
            if (ENTRADA.toString().length()>6) {
                    if (ENTRADA.toString().substring(2,3).equals("-")) {
                            FFECHA = ENTRADA.toString(); 
                    } else {
                            FFECHA = fn.format(ENTRADA);
                    }
            } else {
                    FFECHA = ENTRADA.toString();
            }
            Date AFECHA = new Date();
            fn = new SimpleDateFormat (FORMATO, Locale.getDefault());

            try {
                    if (FFECHA.length()==2) FFECHA = FFECHA + FORMATO_FECHA(AFECHA,"dd-MM-yyyy").substring(2); 
                    if (FFECHA.length()==4) FFECHA = FFECHA.substring(0,2) + "-" + FFECHA.substring(2) + FORMATO_FECHA(AFECHA,"dd-MM-yyyy").substring(5); 
                    if (FFECHA.length()==6) FFECHA = FFECHA.substring(0,2) + "-" + FFECHA.substring(2) + "-" + FFECHA.substring(4);

                    String SFECHA = fn.format(FFECHA);
                    return SFECHA;

            } catch (Exception e) {
                    return FFECHA;
            }

	}
	
	/************************************************************************
	 * Muestra mensajes del programa
	 * @param Mensaje
	 * @param ex = Excepción
	 ************************************************************************/
    public static void MuestraMensaje (String Mensaje, Exception ex){
        
        StringBuffer details = new StringBuffer();
        Throwable causes = ex;
        while(causes.getCause() != null){
            details.append(ex.getMessage());
            details.append("  Causado por:");
            details.append(causes.getCause().getMessage());
            causes = causes.getCause();
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), details.toString());
        FacesContext.getCurrentInstance().addMessage(null, message);
        
    }
	
    public static void MuestraMensaje (String Mensaje){
        StringBuffer details = new StringBuffer();
        // details.append("Error de Proceso" + "\n\n");
    	details.append(Mensaje);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, details.toString(), "error");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	/**
	 * Accede a los valores de una clase 
	 * @param pClase
	 * @return
	 */
	public static Object ObtenerClase(Class pClase) {
		Object resultado = null;
		
		// Obtener nombre de la clase sin el package
		int posPunto = pClase.getName().lastIndexOf(".");
		String claseNombre = pClase.getName().substring(posPunto + 1);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ELContext elContext = facesContext.getELContext();
	    Application application = facesContext.getApplication();
	    ExpressionFactory expressionFactory = application.getExpressionFactory(); 
	    ValueExpression ve = expressionFactory.createValueExpression(elContext, "#{" + claseNombre + "}", pClase);
	    resultado = ve.getValue(elContext);
	    
	    return resultado;
	}
        
    public static void Log(String mensaje) {
        System.out.println(new Date().toString() + " " + mensaje);
    }

}
