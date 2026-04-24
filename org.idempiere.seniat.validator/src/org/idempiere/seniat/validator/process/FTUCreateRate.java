package org.idempiere.seniat.validator.process;

import org.adempiere.base.annotation.Process;
import org.adempiere.exceptions.AdempiereException;

import org.idempiere.seniat.validator.model.FTUMConversionRate;

import org.compiere.model.MConversionRate;
import org.compiere.model.MConversionType;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.idempiere.seniat.validator.base.CustomProcess;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Locale;
import java.util.logging.Level;

@Process
public class FTUCreateRate extends CustomProcess {

    private int p_C_Currency_ID = 0;
    private int p_C_Currency_ID_To = 0;
    private int p_AD_Client_ID = 0; // Parámetro para Grupo Empresarial
	private String 	msg = "";
    @Override
    protected void prepare() {
        for (ProcessInfoParameter para : getParameter()) {
            String name = para.getParameterName();
            if (para.getParameter() == null) {
                // No hacer nada si el parámetro es nulo
            } else if (name.equals("C_Currency_ID")) {
                p_C_Currency_ID = para.getParameterAsInt();
            } else if (name.equals("C_Currency_ID_To")) {
                p_C_Currency_ID_To = para.getParameterAsInt();
            } else if (name.equals("AD_Client_ID")) {
                p_AD_Client_ID = para.getParameterAsInt();
            }
        }
    }

    // Método para desactivar la verificación SSL
    private void disableSSLVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al desactivar la verificación SSL: ", e);
            throw new IllegalArgumentException("Error al desactivar la verificación SSL: ", e);
        }
    }

 // Método para manejar la validación y creación de la tasa de cambio
    private void handleExchangeRate(BigDecimal exchangeRate, java.util.Date spotDate) {
            // Paso 1: Validar si ya existe una tasa creada para hoy
            if (isRateCreatedToday(p_C_Currency_ID, p_C_Currency_ID_To)) {
                log.warning("Ya existe una tasa de conversión creada para el día de hoy.");
                throw new AdempiereException("Ya existe una tasa de conversión creada para el día de hoy.");
            }

            // Paso 2: Validar la fecha proporcionada
            // Si la fecha proporcionada es futura, usamos la tasa de ayer
            if (spotDate.after(new java.util.Date())) {
                log.warning("Error: La fecha proporcionada es futura. Se usará la tasa de ayer para crear la tasa de conversión.");
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.add(java.util.Calendar.DATE, -1); // Restar un día
                java.util.Date spotDatepost = cal.getTime();
                spotDate = new Timestamp(System.currentTimeMillis());
                log.warning("La tasa de conversión para el día de hoy será igual a la de ayer: " + spotDatepost);
                // Si la fecha es válida, obtenemos la última tasa (si es necesario)
                BigDecimal lastRate = getLastRate(p_C_Currency_ID, p_C_Currency_ID_To);
                if (lastRate == null || lastRate.compareTo(BigDecimal.ZERO) == 0) {
                    log.warning("No se encontró ninguna tasa de conversión anterior. Es necesario establecer una tasa inicial.");
                    throw new AdempiereException("No se encontró ninguna tasa de conversión anterior. Es necesario establecer una tasa inicial.");
                }else {
                	createConversionRate(lastRate, spotDate);
                }
            }else {
                // Paso 3: Crear la nueva tasa usando la tasa de ayer si no existe una para hoy
                createConversionRate(exchangeRate, spotDate);
            }


    }

    private BigDecimal getLastRate(int curFromId, int curToId) {
        int clientId = p_AD_Client_ID > 0 ? p_AD_Client_ID : getAD_Client_ID();
        // Consulta SQL para obtener la última tasa de conversión antes de hoy
        String sql = "SELECT MultiplyRate FROM C_Conversion_Rate " +
                     "WHERE C_Currency_ID = ? AND C_Currency_ID_To = ? AND ValidFrom < CURRENT_DATE " +
                     "AND AD_Client_ID = ? " +
                     "ORDER BY ValidFrom DESC LIMIT 1";

        // Ejecutar la consulta y obtener el resultado
        BigDecimal rate = DB.getSQLValueBD(get_TrxName(), sql, curFromId, curToId, clientId);

        // Si no hay resultados, devolver null o BigDecimal.ZERO según tu lógica
        return rate != null ? rate : BigDecimal.ZERO; // o null si prefieres
    }

    // Método para crear la tasa de conversión
    private String createConversionRate(BigDecimal exchangeRate, java.util.Date spotDate) {
        FTUMConversionRate rate = new FTUMConversionRate(getCtx(), 0, get_TrxName());
        int clientId = p_AD_Client_ID > 0 ? p_AD_Client_ID : getAD_Client_ID();
        rate.setClientOrg(clientId, 0); // Asigna AD_Client_ID (Grupo Empresarial) y AD_Org_ID a 0 (*)
        rate.setC_Currency_ID(p_C_Currency_ID);
        rate.setC_Currency_ID_To(p_C_Currency_ID_To);
        rate.setMultiplyRate(exchangeRate);
        rate.setC_ConversionType_ID(MConversionType.getDefault(clientId));
        rate.setValidFrom(new java.sql.Timestamp(spotDate.getTime()));
        rate.setValidTo(new java.sql.Timestamp(spotDate.getTime())); // Misma fecha para ValidTo
        rate.saveEx();
        log.warning("Tasa de conversión creada exitosamente.");
        addBufferLog(rate.getC_Conversion_Rate_ID(), new Timestamp(System.currentTimeMillis()),null, ""+exchangeRate+" "+spotDate, MConversionRate.Table_ID, rate.getC_Conversion_Rate_ID());
		//	Message
		msg = "Tasa de conversión creada exitosamente, Fecha: "+spotDate+" - Tasa: "+exchangeRate;

		return msg;
    }

 // Método para verificar si ya existe una tasa creada hoy
    private boolean isRateCreatedToday(int curFromId, int curToId) {
        int clientId = p_AD_Client_ID > 0 ? p_AD_Client_ID : getAD_Client_ID();
        String whereClause = "C_Currency_ID = ? AND C_Currency_ID_To = ? AND ValidFrom = CURRENT_DATE AND AD_Client_ID = ?";
        Query query = new Query(getCtx(), FTUMConversionRate.Table_Name, whereClause, get_TrxName())
                              .setParameters(curFromId, curToId, clientId);
        return query.match(); // Devuelve true si existe al menos un registro
    }
    
    @Override
    protected String doIt() throws Exception {
        String url = "https://www.bcv.org.ve/";

        // Desactivar la verificación SSL
        disableSSLVerification();

        BigDecimal exchangeRate = null;
        java.util.Date spotDate = null;

        try {
            // Conectar a la página y obtener el HTML
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3")
                    .get();

            // Extraer la fecha
            Element dateElement = doc.select("div.pull-right.dinpro.center span.date-display-single").first();
            if (dateElement != null) {
                String fecha = dateElement.text().trim();
                log.warning("Fecha Valor: " + fecha);

                // Convertir la fecha extraída a un formato Date
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("es", "ES"));
                    spotDate = sdf.parse(fecha);
                } catch (Exception e) {
                	throw new AdempiereUserError( "Error al convertir la fecha: ", e);
                }
            } else {
            	throw new AdempiereUserError("No se encontró la fecha en la página.");
            }

            // Extraer la tasa de cambio (si está disponible en un lugar específico)
            Elements exchangeRateElements = doc.select("#dolar .col-sm-6.centrado strong");
            if (!exchangeRateElements.isEmpty()) {
                Element exchangeRateElement = exchangeRateElements.first();
                String exchangeRateText = exchangeRateElement.text().trim();
                log.warning("Tipo de cambio USD: " + exchangeRateText);

                // Convertir el texto de la tasa de cambio a BigDecimal
                try {
                    exchangeRate = new BigDecimal(exchangeRateText.replace(",", "."));

                    int clientId = p_AD_Client_ID > 0 ? p_AD_Client_ID : Env.getAD_Client_ID(getCtx());
                    // Obtener la precisión y el método de redondeo desde SysConfig
                    int precision = MSysConfig.getIntValue("FTU_EXCHANGE_RATE_PRECISION", 2, clientId, 0);
                    log.warning("Precisión obtenida de SysConfig: " + precision);
                    String roundingModeConfig = MSysConfig.getValue("FTU_EXCHANGE_RATE_ROUNDING_MODE", "HALF_UP", clientId, 0);
                    log.warning("Método de redondeo obtenido de SysConfig: " + roundingModeConfig);
                    RoundingMode roundingMode;
                    try {
                        roundingMode = RoundingMode.valueOf(roundingModeConfig);
                    } catch (IllegalArgumentException e) {
                        log.warning("Método de redondeo no válido en SysConfig. Usando DOWN por defecto.");
                        roundingMode = RoundingMode.HALF_UP;
                    }
                    log.warning("Método de redondeo aplicado: " + roundingMode);

                    // Aplicar la precisión y el método de redondeo
                    exchangeRate = exchangeRate.setScale(precision, roundingMode);
                    log.warning("Tasa de cambio después de aplicar precisión y redondeo: " + exchangeRate);

                    if (exchangeRate.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new AdempiereUserError("La tasa de cambio no es válida: " + exchangeRateText);
                    }
                } catch (NumberFormatException e) {
                    throw new AdempiereUserError("Error al convertir la tasa de cambio: " + exchangeRateText, e);
                }
            } else {
                throw new AdempiereUserError("No se encontró el tipo de cambio en la página.");
            }
        } catch (Exception e) {
        	throw new AdempiereUserError("Error al obtener los datos de la página: ", e);
        }

        // Si se obtuvo la tasa de cambio y la fecha, crear la tasa de conversión
        if (exchangeRate != null && spotDate != null) {
            handleExchangeRate(exchangeRate, spotDate);
        }
        
        return msg;
    }
}