package presentacion;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import datatypes.DtBibliotecario;

import interfaces.IControlador;

public class BibliotecariosRegistradosWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private IControlador icon;

    private JLabel title;
    private JList<String> lectores_list;
    private JPanel list_panel;
    private JScrollPane scroll_list;

    public BibliotecariosRegistradosWindow(IControlador icon){
        this.icon=icon;   
        initialize();
        create_components(icon);
    }

    public void initialize(){
        setTitle("Bibliotecarios registrados");
        setSize(1000, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void create_components(IControlador icon){
        list_panel = new JPanel(new BorderLayout());

        title = new JLabel("Resumen de Bibliotecarios");

        ArrayList<DtBibliotecario> datalist = icon.obtenerDataBibliotecario();
        ArrayList<String> lectores_data = new ArrayList<String>();
        for(DtBibliotecario d: datalist){
            lectores_data.add(((DtBibliotecario) d).toString());
        }

        String[] array_lectores = lectores_data.toArray(new String[0]);
        lectores_list = new JList<>(array_lectores);
        
        scroll_list = new JScrollPane(lectores_list);

        list_panel.add(title, BorderLayout.NORTH);
        list_panel.add(scroll_list, BorderLayout.CENTER);
        this.add(list_panel);
    }

}
