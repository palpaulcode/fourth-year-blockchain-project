package project.blockchain.inputValidator;

import com.jfoenix.controls.JFXTextArea;

public class AlphanumericTextArea extends JFXTextArea {
    public AlphanumericTextArea(){
        this.setPromptText("Alpahanumerics only");
    }

    @Override
    public void replaceText(int start, int end, String text) { // \t space and tab
        if (text.matches("^[A-Za-z0-9\\s]$") || text.isEmpty()){ // \t\r\n\v\f whitespce characters \s works for space and enter
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
