package presentacion;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;

public class FilterComboBox extends JComboBox<String> {
    public FilterComboBox(ArrayList<String> items) {
        super(items.toArray(new String[0]));
        this.setEditable(true); // Make the combobox writable/editable
        this.items = items; // Store the items in a field
        this.lastQuery = ""; // Track the last query to avoid redundant filtering

        // Add document listener with debouncing
        this.editorComponent = (JTextComponent) this.getEditor().getEditorComponent();
        editorComponent.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                scheduleFilter(editorComponent.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                scheduleFilter(editorComponent.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                scheduleFilter(editorComponent.getText());
            }
        });
    }
    
    private ArrayList<String> items; // Field to store the original items
    private String lastQuery; // Track the last query to avoid redundant filtering
    private Timer filterTimer; // Timer for debouncing
    private JTextComponent editorComponent; // Store editor component reference

    private void scheduleFilter(String query) {
        if (filterTimer != null) {
            filterTimer.cancel();
        }
        filterTimer = new Timer();
        filterTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!query.equals(lastQuery)) {
                    Filter(query);
                    lastQuery = query;
                }
            }
        }, 300); // 300ms debounce delay
    }

public void Filter(String query) {
    int caretPosition = editorComponent.getCaretPosition();
    DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) this.getModel();
    model.removeAllElements();
    model.addElement("");
    for (String item : items) {
        if (item.toLowerCase().contains(query.toLowerCase())) {
            model.addElement(item);
        }   
    }
    // Preserve user input and cursor position
    this.getEditor().setItem(query);
    editorComponent.setCaretPosition(caretPosition);
}
    
}
