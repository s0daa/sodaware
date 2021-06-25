package me.soda.sodaware.client.auth;

import me.soda.sodaware.Sodaware;
import java.awt.event.ActionEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameUtil {

    public static final String KEY = "sodabitchnigga";

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

            copyToClipboard(HWIDUtil.getEncryptedHWID(KEY));
            String message = "You are not allowed to use this";
            JOptionPane.showMessageDialog(this, message, "Verify Failed", JOptionPane.PLAIN_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }

        public static void copyToClipboard(String s) {
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}
