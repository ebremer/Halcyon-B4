package com.ebremer.halcyon.gui;

import com.ebremer.halcyon.sparql.Sparql;
import com.ebremer.halcyon.wicket.ListImages;
import com.ebremer.halcyon.HalcyonSettings;
import com.ebremer.halcyon.datum.DataCore;
import com.ebremer.halcyon.wicket.AccountPage;
import com.ebremer.halcyon.wicket.AdminPage;
import com.ebremer.halcyon.wicket.BasePage;
import com.ebremer.halcyon.wicket.ethereal.Graph3D;
import com.ebremer.halcyon.wicket.ethereal.Zephyr;
import com.ebremer.multiviewer.MultiViewer;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class HalcyonApplication extends WebApplication {
    private static final HalcyonSettings settings = HalcyonSettings.getSettings();
    private final DataCore datacore = DataCore.getInstance();
    
    public HalcyonSettings getSettings() {
        return settings;
    }

    public DataCore getDataCore() {
        return datacore;
    }    

    @Override
    public Class<? extends WebPage> getHomePage() {
	return HomePage.class;
    }
    
    @Override
    public Session newSession(Request request, Response response) {
        System.out.println("NEW SESSION CREATED!!!!!!!");
        return new HalcyonSession(request);
    }

    @Override
    public void init() {
	super.init();
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.ERROR);
        ch.qos.logback.classic.Logger jena = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.jena");
        jena.setLevel(ch.qos.logback.classic.Level.ERROR);
        ch.qos.logback.classic.Logger XX = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(org.apache.wicket.util.thread.Task.class);
        XX.setLevel(ch.qos.logback.classic.Level.ERROR);
        
        //System.setProperty("arrow.memory.debug.allocator", "true");
        //ch.qos.logback.classic.Logger arrow = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.arrow");
        //arrow.setLevel(ch.qos.logback.classic.Level.TRACE);
              
        this.getRequestLoggerSettings().setRequestLoggerEnabled(true);
        this.getRequestLoggerSettings().setRecordSessionSize(true);
        if (RuntimeConfigurationType.DEVELOPMENT.equals(getConfigurationType())) {
            getComponentPostOnBeforeRenderListeners().add(new StatelessChecker());
        }
        getCspSettings().blocking().disabled();
        mountPage("/gui", HomePage.class);
        mountPage("/gui/adminme", AdminPage.class);
        mountPage("/gui/accountpage", AccountPage.class);
        mountPage("/gui/login", Login.class);
        mountPage("/gui/yay", BasePage.class);
        mountPage("/gui/ListImages", ListImages.class);
       // mountPage("/gui/ListFeatures", ListFeatures.class);
        mountPage("/gui/viewer", MultiViewer.class); 
        mountPage("/gui/collections", Collections.class);
        mountPage("/gui/sparql", Sparql.class);
        mountPage("/gui/about", About.class);
        mountPage("/gui/threed", Graph3D.class);
        mountPage("/gui/zephyr", Zephyr.class);
        //mountPage("/gui/dicom", DICOM.class);
        //mountPage("/gui/dicom2", DCM.class);
    }
        
    @Override
    public RuntimeConfigurationType getConfigurationType() {
        return RuntimeConfigurationType.DEVELOPMENT;
        //return RuntimeConfigurationType.DEPLOYMENT;
    }
}
