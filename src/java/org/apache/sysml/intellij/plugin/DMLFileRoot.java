package org.apache.sysml.intellij.plugin;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by luluorta on 15-12-17.
 */
public class DMLFileRoot extends PsiFileBase {
    public DMLFileRoot(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, DMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return DMLFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "SystemML DML script file";
    }

    @Override
    public Icon getIcon(int flags) {
        return Icons.FILE;
    }

    @NotNull
    @Override
    public PsiElement[] getChildren() {
        return super.getChildren();
    }
}