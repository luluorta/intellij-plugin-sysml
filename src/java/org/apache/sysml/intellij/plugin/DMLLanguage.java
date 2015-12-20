package org.apache.sysml.intellij.plugin;

import com.intellij.lang.Language;

/**
 * Created by luluorta on 15-12-16.
 */
public class DMLLanguage extends Language {
    public static final DMLLanguage INSTANCE = new DMLLanguage();

    private DMLLanguage() {
        super("DML");
    }
}
