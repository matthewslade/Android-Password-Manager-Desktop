package dpm;

import dpm.model.PasswordList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainController
{

    private PasswordList passwordList;

    @FXML
    protected ListView<String> listView;

    @FXML
    public void initialize()
    {
        passwordList = AuthenticationManager.INSTANCE.getPasswordList();

        if(passwordList!=null && passwordList.getPasswords()!=null)
        {
            List<String> stringList = new ArrayList<>();
            stringList.addAll(passwordList.getPasswords().keySet());
            Collections.sort(stringList);
            ObservableList<String> items = FXCollections.observableArrayList(stringList);
            listView.setItems(items);
            listView.setOnMouseClicked(event -> {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(AuthenticationManager.INSTANCE.generatePassword(passwordList.getPasswords().get(listView.getSelectionModel().getSelectedItem())));
                clipboard.setContent(content);
            });


        }
    }
}
