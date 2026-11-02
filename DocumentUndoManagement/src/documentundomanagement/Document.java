
package documentundomanagement;

public class Document {

    // Private attribute (Encapsulation)
    private String content;

    // Constructor
    public Document() {
        content = "";
    }

    // Constructor with initial text
    public Document(String content) {
        this.content = content;
    }

    // Getter
    public String getContent() {
        return content;
    }

    // Setter
    public void setContent(String content) {

        if (content != null) {
            this.content = content;
        }
    }

    // Check if document is empty
    public boolean isEmpty() {
        return content.trim().isEmpty();
    }

    // Clear document
    public void clear() {
        content = "";
    }

    // Count characters
    public int getCharacterCount() {
        return content.length();
    }
}
