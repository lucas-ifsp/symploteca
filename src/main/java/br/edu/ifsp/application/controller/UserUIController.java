package br.edu.ifsp.application.controller;

import br.edu.ifsp.application.view.WindowLoader;
import br.edu.ifsp.domain.entities.user.Faculty;
import br.edu.ifsp.domain.entities.user.Student;
import br.edu.ifsp.domain.entities.user.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

import static br.edu.ifsp.application.main.Main.*;

public class UserUIController {
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtInstitutionId;
    @FXML
    private TextField txtDepartment;
    @FXML
    private TextField txtCourse;
    @FXML
    private CheckBox ckFaculty;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnConfirm;

    private User user;

    public void configureUserViewType(ActionEvent actionEvent) {
        boolean trueIfFacult = ckFaculty.isSelected();
        txtDepartment.setDisable(!trueIfFacult);
        txtCourse.setDisable(trueIfFacult);
    }

    public void saveOrUpdate(ActionEvent actionEvent) throws IOException {
        getEntityFromView();
        boolean newUser = findUserUseCase.findOne(user.getInstitutionalId()).isEmpty();

        if (newUser) {
            createUserUseCase.insert(user);
        } else {
            updateUserUseCase.update(user);
        }
        WindowLoader.setRoot("UserManagementUI");
    }

    private void getEntityFromView() {
        if (user == null) {
            createUserProperKind();
        }
        user.setName(txtName.getText());
        user.setEmail(txtEmail.getText());
        user.setPhone(txtPhone.getText());
    }

    private void createUserProperKind() {
        if (ckFaculty.isSelected()) {
            Faculty faculty = new Faculty();
            faculty.setDepartment(txtDepartment.getText());
            user = faculty;
        } else {
            Student student = new Student();
            student.setCourse(txtCourse.getText());
            user = student;
        }
        user.setInstitutionalId(txtInstitutionId.getText().trim().toUpperCase());
    }

    public void backToPreviousScene(ActionEvent actionEvent) throws IOException {
        WindowLoader.setRoot("UserManagementUI");
    }

    public void setUser(User user, UIMode mode) {
        if (user == null)
            throw new IllegalArgumentException("User can not be null.");

        this.user = user;
        setEntityIntoView();

        txtInstitutionId.setDisable(true);

        if (mode == UIMode.VIEW)
            configureViewMode();

    }

    private void setEntityIntoView() {
        txtEmail.setText(user.getEmail());
        txtName.setText(user.getName());
        txtPhone.setText(user.getPhone());
        txtInstitutionId.setText(user.getInstitutionalId());

        boolean faculty = user instanceof Faculty;
        ckFaculty.setSelected(faculty);
        ckFaculty.setDisable(true);

        if (faculty) {
            txtDepartment.setText(((Faculty) user).getDepartment());
            txtDepartment.setDisable(false);
            txtCourse.setDisable(true);
        } else {
            txtCourse.setText(((Student) user).getCourse());
            txtDepartment.setDisable(true);
        }
    }

    private void configureViewMode() {
        btnCancel.setLayoutX(btnConfirm.getLayoutX());
        btnCancel.setLayoutY(btnConfirm.getLayoutY());
        btnCancel.setText("Fechar");

        btnConfirm.setVisible(false);

        txtCourse.setDisable(true);
        txtDepartment.setDisable(true);
        txtEmail.setDisable(true);
        txtName.setDisable(true);
        txtPhone.setDisable(true);
        ckFaculty.setDisable(true);
    }
}
