package br.edu.ifsp.domain.usecases.user;

import br.edu.ifsp.domain.entities.user.User;
import br.edu.ifsp.domain.usecases.utils.EntityNotFoundException;
import br.edu.ifsp.domain.usecases.utils.Notification;
import br.edu.ifsp.domain.usecases.utils.Validator;

public class UpdateUserUseCase {
    private UserDAO userDAO;

    public UpdateUserUseCase(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean update(User user) {
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErros())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = user.getInstitutionalId();
        if (userDAO.findOne(id).isEmpty())
            throw new EntityNotFoundException("User not found.");

        return userDAO.update(user);
    }
}
