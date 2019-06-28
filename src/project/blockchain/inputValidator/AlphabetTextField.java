package project.blockchain.inputValidator;

import com.jfoenix.controls.JFXTextField;

public class AlphabetTextField extends JFXTextField {
    public AlphabetTextField(){
        this.setText(""); //can be set to something like "use alphabets only"
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("^[A-Za-z\\s]$") || text.isEmpty()){
            super.replaceText(start, end, text);
        }
        /*else {
            super.replaceText(start, end, "invalid entry"); //working well
        }*/
    }

    @Override
    public void replaceSelection(String replacement) {
        super.replaceSelection(replacement);
    }
}
