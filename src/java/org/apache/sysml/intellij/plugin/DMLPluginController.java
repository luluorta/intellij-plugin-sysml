package org.apache.sysml.intellij.plugin;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLPluginController implements ProjectComponent {
    public static final String PLUGIN_ID = "org.apache.sysml.intellij.plugin";
    public static final Logger LOG = Logger.getInstance("DMLPluginController");
    
    public boolean projectIsClosed = false;

    public Project project;

    public DMLPluginController(Project project) {
        this.project = project;
    }

    public static DMLPluginController getInstance(Project project) {
        if ( project==null ) {
            LOG.error("getInstance: project is null");
            return null;
        }
        DMLPluginController pc = project.getComponent(DMLPluginController.class);
        if ( pc==null ) {
            LOG.error("getInstance: getComponent() for "+project.getName()+" returns null");
        }
        return pc;
    }
    
    @Override
    public void projectOpened() {
        IdeaPluginDescriptor plugin = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID));
        String version = "unknown";
        if ( plugin!=null ) {
            version = plugin.getVersion();
        }
        LOG.info("ANTLR 4 Plugin version "+version+", Java version "+ SystemInfo.JAVA_VERSION);
        // make sure the tool windows are created early
        //createToolWindows();
        //installListeners();
    }

    @Override
    public void projectClosed() {
        LOG.info("projectClosed " + project.getName());
        //synchronized ( shutdownLock ) { // They should be called from EDT only so no lock
        projectIsClosed = true;

        project = null;
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() { return "systemml.ProjectComponent"; }
}
