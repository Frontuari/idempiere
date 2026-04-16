package org.idempiere.seniat.validator.process;

import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;

import org.adempiere.base.annotation.Process;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.SimplePDFViewer;
import org.compiere.model.MInvoice;
import org.compiere.model.MInOut;
import org.compiere.model.MMovement;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ServerProcessCtl;

import org.idempiere.seniat.validator.base.CustomProcess;

@Process
public class ReprintDocument extends CustomProcess {

    private String p_DocBaseType = null;
    private int p_C_Invoice_ID = 0;
    private int p_M_Movement_ID = 0;
    private int p_M_InOut_ID = 0;

    private File printedPDF = null;

    @Override
    protected void prepare() {
        for (var parameter : getParameter()) {
            String name = parameter.getParameterName();
            if (parameter.getParameter() == null) continue;

            switch (name) {
                case "DocBaseType":
                    p_DocBaseType = parameter.getParameterAsString();
                    break;
                case "C_Invoice_ID":
                    p_C_Invoice_ID = parameter.getParameterAsInt();
                    break;
                case "M_Movement_ID":
                    p_M_Movement_ID = parameter.getParameterAsInt();
                    break;
                case "M_InOut_ID":
                    p_M_InOut_ID = parameter.getParameterAsInt();
                    break;
            }
        }
    }

    @Override
    protected String doIt() throws Exception {
        if (p_DocBaseType == null || p_DocBaseType.isEmpty()) {
            throw new AdempiereException("El parámetro DocBaseType es obligatorio.");
        }

        switch (p_DocBaseType) {
            case "ARI":
            case "ARC":
                if (p_C_Invoice_ID == 0)
                    throw new AdempiereException("C_Invoice_ID es obligatorio para facturas.");
                printedPDF = printInvoice(p_C_Invoice_ID);
                break;
            case "MMM":
                if (p_M_Movement_ID == 0)
                    throw new AdempiereException("M_Movement_ID es obligatorio para movimientos.");
                printedPDF = printMovement(p_M_Movement_ID);
                break;
            case "MMS":
                if (p_M_InOut_ID == 0)
                    throw new AdempiereException("M_InOut_ID es obligatorio para remitos.");
                printedPDF = printInOut(p_M_InOut_ID);
                break;
            default:
                throw new AdempiereException("Tipo de documento no reconocido: " + p_DocBaseType);
        }

        if (printedPDF == null || !printedPDF.exists()) {
            throw new AdempiereException("No se generó el PDF para " + p_DocBaseType);
        }

        return "Documento reimpreso exitosamente.";
    }

    private File printInvoice(int invoiceID) {
        MInvoice invoice = new MInvoice(getCtx(), invoiceID, get_TrxName());
        return invoice.createPDF();
    }

    private File printMovement(int movementID) throws Exception {
        // Implementación directa sin modificar MMovement
        // Usa ReportEngine con el AD_PrintFormat_ID de movimientos
        // Si ReportEngine.MOVEMENT no existe, reemplaza con el ID real de tu formato de impresión de M_Movement

        int movementPrintFormatID = ReportEngine.MOVEMENT; // Reemplaza si no existe en tu versión

        ReportEngine re = ReportEngine.get(getCtx(), movementPrintFormatID, movementID, get_TrxName());
        if (re == null) {
            log.warning("No ReportEngine para MMovement_ID=" + movementID);
            return null;
        }

        MPrintFormat format = re.getPrintFormat();
        File pdfFile = null;

        // Si es Jasper
        if (format.getJasperProcess_ID() > 0) {
            ProcessInfo pi = new ProcessInfo("", format.getJasperProcess_ID());
            pi.setRecord_ID(movementID);
            pi.setIsBatch(true);
            pi.setTransientObject(format);

            ServerProcessCtl.process(pi, null);
            pdfFile = pi.getPDFReport();

            if (pdfFile == null) {
                throw new AdempiereException("No se generó el PDF con Jasper para movimiento ID=" + movementID);
            }
            log.info("PDF Jasper generado para movimiento: " + pdfFile.getAbsolutePath());
        } 
        // Si es Print Format estándar
        else {
            File temp = File.createTempFile("MMovement" + movementID + "_", ".pdf");
            pdfFile = re.getPDF(temp);
            if (pdfFile == null) {
                throw new AdempiereException("No se generó el PDF estándar para movimiento ID=" + movementID);
            }
            log.info("PDF estándar generado para movimiento: " + pdfFile.getAbsolutePath());
        }

        return pdfFile;
    }

    private File printInOut(int InOutID) {
        MInOut InOut = new MInOut(getCtx(), InOutID, get_TrxName());
        return InOut.createPDF();
    }

    @Override
    protected void postProcess(boolean success) {
        if (success && printedPDF != null && printedPDF.exists()) {
            if (processUI != null) {
                AEnv.executeAsyncDesktopTask(() -> {
                    try {
                        String title = getProcessInfo().getTitle();
                        Window win = new SimplePDFViewer(title, new FileInputStream(printedPDF));
                        SessionManager.getAppDesktop().showWindow(win, "center");
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Error mostrando PDF: " + e.getMessage(), e);
                    }
                });
            }
        }
    }
}
