package org.apache.sysml.intellij.plugin.configdialogs;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.apache.sysml.intellij.plugin.DMLSyntaxHighlighter;
import org.apache.sysml.intellij.plugin.Icons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * Created by luluorta on 15-12-17.
 */
public class DMLColorsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] ATTRIBUTES =
            {
                    //new AttributesDescriptor("Lexer Rule", DMLSyntaxHighlighter.KEYWORD),
                    new AttributesDescriptor("Builtin Function Calls", DMLSyntaxHighlighter.BUILTIN),
                    //new AttributesDescriptor("Lexer Rule", DMLSyntaxHighlighter.FUNCID),
                    new AttributesDescriptor("Function Calls", DMLSyntaxHighlighter.FUNCCALL),
                    new AttributesDescriptor("Variable Type", DMLSyntaxHighlighter.TYPE),
            };

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new DMLSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return
                "fileV      = $V;\n" +
                        "fileL      = $L;\n" +
                        "fileR      = $R;\n" +
                        "\n" +
                        "r          = ifdef ($rank, 10);\n" +
                        "\n" +
                        "V_nonzero_ind = ppred (V, 0, \"!=\");\n" +
                        "row_nonzeros = rowSums (V_nonzero_ind);\n" +
                        "col_nonzeros = t (colSums (V_nonzero_ind));\n" +
                        "[z_norm_m, z_norm_e] = round_to_print (z_norm);\n" +
                        "round_to_print = function (double x_to_truncate)\n" +
                        "return (double mantissa, int eee)\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRIBUTES;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "DML";
    }
}
