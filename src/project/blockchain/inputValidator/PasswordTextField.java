package project.blockchain.inputValidator;

import com.jfoenix.controls.JFXPasswordField;

public class PasswordTextField extends JFXPasswordField {
    public PasswordTextField(){
        this.setPromptText("Enter password");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("^[A-Za-z0-9]|[?|/>.<,:;'@#~!£$%^&*()-_+={}`¬]$") || text.isEmpty()){
            super.replaceText(start, end, text);
        }
        //include else to highlight in red color in case of wrong entry
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
