package cap.gui;

import cap.ResultPart;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.util.Collection;

import javax.swing.JFrame;

/**
 * Authors: Bernd Schmidt, Robert Könitz
 */
public class DebugGui extends JFrame {
    private static final long serialVersionUID = -858161075567555650L;

    private ResultLoaderPanel loader = new ResultLoaderPanel();

    public DebugGui() throws HeadlessException {
        super("Captchalize Debug GUI");
        this.init();
    }

    public DebugGui(final String title) throws HeadlessException {
        super(title);
        this.init();
    }

    public void setResultData(final Collection<ResultPart> parts) {
        this.loader.setResultData(parts);

        this.pack();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.getContentPane().add(this.loader);
        this.pack();
        this.setVisible(true);
    }
}
