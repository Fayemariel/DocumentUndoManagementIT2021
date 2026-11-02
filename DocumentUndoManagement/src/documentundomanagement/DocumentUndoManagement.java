
package documentundomanagement;

public class DocumentUndoManagement {

    public static void main(String[] args) {

        // Start the application
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                DocumentWindow window = new DocumentWindow();

                window.setVisible(true);
            }
        });
    }
}
