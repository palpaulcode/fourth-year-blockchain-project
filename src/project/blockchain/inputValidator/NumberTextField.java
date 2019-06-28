package project.blockchain.inputValidator;

import com.jfoenix.controls.JFXTextField;

public class NumberTextField extends JFXTextField {
    public NumberTextField(){
        this.setPromptText("Enter Numbers only");
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("^[0-9]$") || text.isEmpty()){
            super.replaceText(start, end, text);
        }
        //include else to highlight with red color when user presses wrong keys
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
