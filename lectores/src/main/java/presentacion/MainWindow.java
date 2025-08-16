package presentacion;

import javax.swing.JFrame;

public class MainWindow {

  private JFrame window;

    public MainWindow() {
        window = new JFrame();
        window.setTitle("PAP2025 - Entrega 1");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800,500);
        window.setLocationRelativeTo(null);
    }

    public void show() {
        window.setVisible(true);
    }
}
