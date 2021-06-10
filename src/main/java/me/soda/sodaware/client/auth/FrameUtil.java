package me.soda.sodaware.client.auth;

import me.soda.sodaware.Sodaware;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class FrameUtil {

    public static void Display() {
        Frame frame = new Frame();
        frame.setVisible(false);
        throw new NoStackTraceThrowable("Verify HWID Failed!");
    }

    public static class Frame extends JFrame {

        public Frame() {
            this.setTitle("Verify Failed");
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLocationRelativeTo(null);
            copyToClipboard(HWIDUtil.getEncryptedHWID(Sodaware.KEY));
            String message = "You are not allowed to use this" + "\n" + "HWID: " + HWIDUtil.getEncryptedHWID(Sodaware.KEY) + "\n(Copied to clipboard)";
            JOptionPane.showMessageDialog(this, message, "Verify Failed", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }

        public static void copyToClipboard(String s) {
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}
