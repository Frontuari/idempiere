package net.frontuari.theme.panel;

import java.io.IOException;

import org.adempiere.webui.panel.HeaderPanel;
import org.adempiere.webui.theme.ThemeManager;
import org.compiere.util.CLogger;
import org.zkoss.zul.Image;

public class FTUHeaderPanel extends HeaderPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6442750584675935215L;
	static CLogger log = CLogger.getCLogger(FTUHeaderPanel.class);
	
	@Override 
    protected void onCreate() { 
        super.onCreate(); 
        Image image = (Image) getFellow("logo"); 
        if (image != null) { 
        	try {
				org.zkoss.image.Image logo = ThemeManager.getClientWebLogo();
				if (logo != null) { 
                    image.setContent(logo);
                } else { 
                	image.setSrc(ThemeManager.getSmallLogo());
                } 
				image.setVisible(true); 
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    } 

}
