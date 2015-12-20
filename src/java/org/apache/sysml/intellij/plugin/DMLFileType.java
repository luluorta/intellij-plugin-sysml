package org.apache.sysml.intellij.plugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLFileType extends LanguageFileType {
    public static final DMLFileType INSTANCE = new DMLFileType();

    private DMLFileType() {
        super(DMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "SystemML DML file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "SystemML DML file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "dml";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.FILE;
    }
}